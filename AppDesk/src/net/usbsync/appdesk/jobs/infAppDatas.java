package net.usbsync.appdesk.jobs;

import java.util.ArrayList;

public interface infAppDatas {
	// 새로운 AppDatas 추가
	public void addAppDatas(AppDatas AppDatas);

	// 아이디에 해당하는 AppDatas 가져오기
	public AppDatas getAppDatas(int id);

	// 모든 AppDatas 리스트 가져오기
	public ArrayList<AppDatas> getAllAppDatas();

	// 가져온 AppDatas 숫자 가져오기
	public int getAppDatassCount();

	// AppDatas 업데이트
	public int updateAppDatas(AppDatas appDatas);

	// AppDatas 삭제하기
	public void deleteAppDatas(AppDatas appDatas);

	public ArrayList<AppDatas> getAppDatasForText(String queryText);

	public void deleteAllAppDatas();
}
