package org.czy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.czy.controller.AutoTimerController;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.TestSumReport;
import org.czy.websocket.WebsocketHandler;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.utils.tools.TestReportdbTools;

/**
 * 
 * @author dengpeng
 *
 */
public class WordReport {
	private final static Logger LOG = Logger.getLogger(WordReport.class);
	
	public static String makeWord(Map<String, String> map,Map<Integer, List<List<String>>> maplist,String pname){
		String newpath = "";
		try {
			String wordpath = Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.WORDPATH;
			if(!new File(wordpath).exists()){
				new File(wordpath).mkdirs();
			}
			newpath = wordpath+File.separator+pname+"_测试总结报告_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".docx";
			//读取word源文件
			FileInputStream fileInputStream = new FileInputStream(wordpath+File.separator+"TestSumupReporter.docx");
			XWPFDocument document = new XWPFDocument(fileInputStream);
			//获取所有表格
			List<XWPFTable> tables = document.getTables();
			// 替换段落中的指定文字
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
			    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
			    Set<String> set = map.keySet();
			    Iterator<String> iterator = set.iterator();
			    while (iterator.hasNext()) {
			        String key = iterator.next();
			        List<XWPFRun> run=paragraph.getRuns();
			         for(int i=0;i<run.size();i++)
			         {
			          if(run.get(i).getText(run.get(i).getTextPosition())!=null && run.get(i).getText(run.get(i).getTextPosition()).equals(key))
			          {    
			            /* 
			             * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
			             * 就可以把原来的文字全部替换掉了
			             */         
			              run.get(i).setText(map.get(key),0);    
			          }    
			         }    
			    }    
			}
			// 替换表格中的指定文字
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
			    XWPFTable table = (XWPFTable) itTable.next();
			    int rcount = table.getNumberOfRows();
			    for (int i = 0; i < rcount; i++) {
			        XWPFTableRow row = table.getRow(i);
			        List<XWPFTableCell> cells = row.getTableCells();
			        for (XWPFTableCell cell : cells) {
			            for (Entry<String, String> e : map.entrySet()) {
			                if (cell.getText().equals(e.getKey())) {
			                    cell.removeParagraph(0);
			                    cell.setText(e.getValue());
			                }
			            }
			        }
			    }
			}
			//2.3列出主要的测试执行情况table
			execMainDesc(maplist, tables);
			//2.4.1	遗留缺陷table
			failLeaveOut(maplist, tables);
			fileInputStream.close();
			//写到目标文件
			OutputStream output = new FileOutputStream(newpath);
			document.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			LOG.error(e);
			return  "";
		} catch (IOException e) {
			LOG.error(e);
			return  "";
		}
		return newpath;
	}

	private static void failLeaveOut(Map<Integer, List<List<String>>> maplist, List<XWPFTable> tables) {
		//2.3列出主要的测试执行情况
		XWPFTable table = tables.get(4);
		List<List<String>> list = maplist.get(4);
		//获取到刚刚插入的行
		for (int i = 0; i < list.size(); i++) {
			List<String> cols = list.get(i);
			XWPFTableRow row = table.createRow();
			//设置单元格内容
			row.getCell(0).setText(cols.get(0));
			row.getCell(1).setText(cols.get(1));
			row.getCell(2).setText(cols.get(2));
		}
	}

	private static void execMainDesc(Map<Integer, List<List<String>>> maplist, List<XWPFTable> tables) {
		//2.3列出主要的测试执行情况
		XWPFTable table = tables.get(3);
		List<List<String>> list = maplist.get(3);
		String up = "";
		int mergeIndex = 0;
		//获取到刚刚插入的行
		for (int i = 0; i < list.size(); i++) {
			List<String> cols = list.get(i);
			if(!up.equals(cols.get(0))){
				if(i!=0&&i-mergeIndex>0){
					mergeCellsVertically(table,0,mergeIndex,i);
				}
				mergeIndex = i+1;
			}
			
			XWPFTableRow row = table.createRow();
			//设置单元格内容
			up = cols.get(0);
			//测试需求
			row.getCell(0).setText(cols.get(0));
			//开发人员
			String p = "";
			for (int j = 5; j < cols.size(); j++) {
				if(null!=cols.get(j)&&"null"!=cols.get(j)){
					p += cols.get(j)+",";
				}
			}
			if(!p.equals("")){
				row.getCell(1).setText(p.substring(0,p.length()-1));
			}else{
				row.getCell(1).setText("");
			}
			//测试用例
			row.getCell(2).setText(cols.get(1));
			//是否评审
			row.getCell(3).setText("是");
			//是否通过
			row.getCell(4).setText(cols.get(4));
			//关联步骤数
			row.getCell(5).setText(cols.get(3));
			//执行步骤数
			row.getCell(6).setText(Integer.valueOf(cols.get(2))*Integer.valueOf(cols.get(3))+"");
			if(i==list.size()-1&&list.size()!=1){
				mergeCellsVertically(table,0,mergeIndex,i+1);
			}
		}
	}
	
	public static String downloadWord(String uid,String pname,Qcdb db,int folderID){
		try {
			TestReportdbTools tools = new TestReportdbTools();
			DecimalFormat df=new DecimalFormat(".00");
			tools.getTableData(folderID, db);
			List<Project> prolist = tools.get_fid(db, folderID);
			readfiledis(uid,tools,prolist,db,folderID);
			tools.getRate();
//			List<String> newlist = null;
//			String testname = "";
//			String flag = "";
//			String user = "";
			int step = 0;
//			String desc = "";
			int count = 1;
			Map<Integer, List<List<String>>> dmap = tools.getMap();
			List<List<String>> mianDescMap = dmap.get(3);
//			List<List<String>> newMianDescMap = new ArrayList<List<String>>();
			int touchStepNum = 0;
			int execStepNum = 0;
			for (int i = 0; i < mianDescMap.size(); i++) {
				List<String> list = mianDescMap.get(i);
//				if(i!=0){
//					if(testname.equals(list.get(1))){
//						++count;
//					}else{
//						newlist = new ArrayList<String>();
//						newlist.add(desc);
//						newlist.add(user);
//						newlist.add(testname);
//						newlist.add(flag);
//						newlist.add(step+"");
//						newlist.add(count*step+"");
//						touchStepNum += step;
//						execStepNum += count*step;
//						newMianDescMap.add(newlist);
//						count = 1; 
//					}
//				}
//				desc = list.get(0);
//				String usernames = "";
//				for (int j = 6; j < list.size(); j++) {
//					if(null!=list.get(j)&&!list.get(j).equals("null")&&!list.get(j).equals("")){
//						usernames += list.get(j)+",";
//					}
//				}
//				if(!usernames.equals("")){
//					user = usernames.substring(0,usernames.length()-1);
//				}
//				testname = list.get(1);
//				flag = list.get(4);
				count = Integer.valueOf(list.get(2));
				step = Integer.valueOf(list.get(3));
//				if(i==mianDescMap.size()-1){
//					newlist = new ArrayList<String>();
//					newlist.add(desc);
//					newlist.add(user);
//					newlist.add(testname);
//					newlist.add(flag);
//					newlist.add(step+"");
//					newlist.add(count*step+"");
//					touchStepNum += step;
//					execStepNum += count*step;
//					newMianDescMap.add(newlist);
//					break;
//				}
				touchStepNum += step;
				execStepNum += count*step;
			}
//			dmap.put(3, newMianDescMap);
//			tools.setMap(dmap);
			
			Map<String, String> map = new HashMap<String, String>();
			TestSumReport tsr = tools.getTs();
			map.put("${failCount}",tsr.getFailCount()+"");
			map.put("${failLeaveOut}",tsr.getFailLeaveOut()+"");
			map.put("${failRate}",Double.valueOf(df.format(tsr.getFailRate()))*100+"%");
			map.put("${urgent}",tsr.getUrgent()+"");
			map.put("${veryHigh}",tsr.getVeryHigh()+"");
			map.put("${high}",tsr.getHigh()+"");
			map.put("${medium}",tsr.getMedium()+"");
			map.put("${low}",tsr.getLow()+"");
			map.put("${rate}",Double.valueOf(df.format(tsr.getRate()))*100+"%");
			map.put("${touchStepNum}",touchStepNum+"");
			map.put("${execStepNum}",execStepNum+"");
			map.put("${execTestCount}",tsr.getExecTestCount()+"");
			map.put("${testRate}","100%");
			map.put("${reviewRate}","100%");
			map.put("${successRate}",Double.valueOf(df.format(tsr.getSuccessRate()))*100+"%");
			return makeWord(map,tools.getMap(),pname);
		} catch (NumberFormatException e) {
			LOG.error(e);
			return "";
		} catch (Exception e) {
			LOG.error(e);
			return "";
		}
	}
	
	private static void readfiledis(String uid,TestReportdbTools tools,List<Project> prolist,Qcdb db,int fatherID) throws Exception{
		if(prolist.size()>0){
			for (Project pro : prolist) {
				int fid = Integer.valueOf(pro.getProjectid());
				tools.getTableData(fid, db);
				List<Project> ps = tools.get_fid(db, fid);
				if(null==WebsocketHandler.websocketSessionsConcurrentHashMap.get(uid)){
					throw new Exception("socket未连接");
				}
				WebsocketHandler.sendMessageToUser(uid, pro.getName());
				LOG.info("总结报告包含["+pro.getName()+"]文件夹->id:"+fid);
				if(ps.size()>0){
					readfiledis(uid,tools,ps,db,fid);
				}
			}
		}else{
			tools.getTableData(fatherID, db);
		}
	}
	
	/**
	 * 跨列合并
	 * @param table word表格
	 * @param row 第几行
	 * @param fromCell 开始列
	 * @param toCell 结束列
	 */
	public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
        
    /**
     * 跨行合并
     * @param table word表格
     * @param col 第几列
     * @param fromRow 开始行
     * @param toRow 结束行
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // 第一个合并单元格设置为重新启动合并值
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // 加入（合并）第一个单元格的单元格是用连续设置的 
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
