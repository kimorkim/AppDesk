package net.usbsync.appdesk.jobs;

import java.util.ArrayList;

public interface infAppDatas {
	// ���ο� AppDatas �߰�
	public void addAppDatas(AppDatas AppDatas);

	// ���̵� �ش��ϴ� AppDatas ��������
	public AppDatas getAppDatas(int id);

	// ��� AppDatas ����Ʈ ��������
	public ArrayList<AppDatas> getAllAppDatass();
	// ������ AppDatas ���� ��������
	public int getAppDatassCount();

	// AppDatas ������Ʈ
	public int updateAppDatas(AppDatas appDatas);

	// AppDatas �����ϱ�
	public void deleteAppDatas(AppDatas appDatas);
}
