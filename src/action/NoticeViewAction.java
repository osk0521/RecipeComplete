package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerServiceDao;
import dao.ProfileDao;
import dao.RecipeBoardDao;
import dao.StoreMainpageDao;
import dto.NoticeDto;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import vo.AdvertisementGoodsVo;

public class NoticeViewAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		// 세션에 로그인 아이디가 있다면 가져오기
		String loginId = (String)session.getAttribute("loginId");
		// 로그인되어있는 아이디가 있다면 페이지 header에 로그인 유저 정보를 표시하기 위해 로그인 유저 프로필 정보 뽑아오기
		if(loginId != null) {
			ProfileDto myProfile = new ProfileDao().getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
		// 최근 본 레시피 가져오기
		Cookie[] cookies = request.getCookies();
		// 최근 본 레시피를 가진 쿠키만 저장하기 위한 리스트
		ArrayList<Cookie> recentRecipeCookies = new ArrayList<Cookie>();
		for(Cookie c : cookies) {
			if(c.getName().contains("recipeId")) {
				System.out.println("최근본레시피 아이디 : " + c.getValue());
				recentRecipeCookies.add(c);
			}
		}
		RecipeBoardDao rbDao = new RecipeBoardDao();
		ArrayList<RecentRecipeDto> recentRecipeList = new ArrayList<RecentRecipeDto>();
		if(recentRecipeCookies.size() != 0) {
			for(int i=recentRecipeCookies.size()-1; i>=0; i--) {
				System.out.println("최근본레시피 배열에 1개 삽입");
				int recentRecipeId = Integer.parseInt( recentRecipeCookies.get(i).getValue() );
				recentRecipeList.add(rbDao.getRecentRecipe(recentRecipeId));
			}
		}
		System.out.println("최근본레시피 개수 : " + recentRecipeCookies.size());
		request.setAttribute("recentRecipeList", recentRecipeList);
		// 하단 우측 베스트상품 5종 가져오기
		StoreMainpageDao storeDao = new StoreMainpageDao();
		ArrayList<AdvertisementGoodsVo> advertisementGoodsList = storeDao.getAdvertisementGoodsList();
		request.setAttribute("advertisementGoodsList", advertisementGoodsList);
		// 공지사항 목록을 가져오기 위한 Dao 생성
		CustomerServiceDao csDao = new CustomerServiceDao();
		// 현재 페이지 번호를 받아오기. 기본값은 1
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));
		} catch(NumberFormatException e) { }; // 잘못된 페이지값을 받을 경우 1로 고정
		// pagination을 위한 시작페이지, 끝페이지 설정
		int page = (pageNum/5 - (pageNum%5==0 ? 1 : 0)) * 5; // 시작, 끝페이지를 정하기 위한 수식
		int startPageNum = page + 1;
		int endPageNum = page + 5;
		int lastPageNum = csDao.getNoticePageNum();
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("lastPageNum", lastPageNum);
		// 공지사항 리스트 생성
		ArrayList<NoticeDto> noticeList = csDao.getNotice(pageNum);
		request.setAttribute("noticeList", noticeList);
		
		// 파라미터로 받은 페이지 수가 총 페이지 수 보다 클 때
		if(pageNum>lastPageNum && lastPageNum!=0) { 
			request.getRequestDispatcher("Controller?command=page_not_found").forward(request, response);
		} else {
			request.getRequestDispatcher("Recipe_Notice.jsp").forward(request, response);
		}
	}
}
