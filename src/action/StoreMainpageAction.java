package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StoreMainpageDao;
import vo.StoreMainpageGoodVo;

public class StoreMainpageAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//메인 페이지 dao 생성
		StoreMainpageDao mainpagedao = new StoreMainpageDao();
		
		ArrayList<String> eventshow = new ArrayList<>(); 
		eventshow = mainpagedao.Showevent(); // event image src get from d/b
		request.setAttribute("event", eventshow);
		
		ArrayList<StoreMainpageGoodVo> HotDealinfoJSP = new ArrayList<>();
		HotDealinfoJSP = mainpagedao.GetHotdealinfo(); // goods information related hotdeal get from d/b
		request.setAttribute("hotdealinfo", HotDealinfoJSP);
		
		ArrayList<StoreMainpageGoodVo> BestDealinfoJSP = new ArrayList<>();
		BestDealinfoJSP = mainpagedao.Getbestdealinfo(); // goods information related best get from d/b
		request.setAttribute("bestdealinfo",BestDealinfoJSP );
		
		ArrayList<StoreMainpageGoodVo> RecentdealinfoJSP = new ArrayList<>();
		RecentdealinfoJSP = mainpagedao.Getrecentdealinfo(); // goods information related recent get from d/b
		request.setAttribute("recentdealinfo", RecentdealinfoJSP);
		
		RequestDispatcher rd = request.getRequestDispatcher("HRK_Store_maimpage.jsp");
		rd.forward(request, response);

	}

}
