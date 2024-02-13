package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerServiceDao;
import dao.ProfileDao;
import dao.RecipeBoardDao;
import dao.StoreMainpageDao;
import dto.InquiryDto;
import dto.ProfileDto;
import dto.RecentRecipeDto;
import vo.AdvertisementGoodsVo;

public class InquiryModifyFormAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아이디 가져오기
		String loginId = (String)request.getSession().getAttribute("loginId");
		// 하단 우측 베스트상품 5종 가져오기
		StoreMainpageDao storeDao = new StoreMainpageDao();
		ArrayList<AdvertisementGoodsVo> advertisementGoodsList = storeDao.getAdvertisementGoodsList();
		request.setAttribute("advertisementGoodsList", advertisementGoodsList);
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
		// 로그인 아이디가 있다면 헤더에 표시할 로그인 유저의 정보를 가져오기
		if(loginId != null) {
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
		// 문의글 정보 가져오기
		int inquiryId = Integer.parseInt(request.getParameter("inquiry_id"));
		CustomerServiceDao csDao = new CustomerServiceDao();
		InquiryDto inquiryObj = csDao.getInquiryDetail(inquiryId);
		// 문의글 정보 request에 저장
		request.setAttribute("inquiryId", inquiryId);
		request.setAttribute("inquiryTitle", inquiryObj.getInquiryTitle());
		request.setAttribute("inquiryContent", inquiryObj.getInquiryContent());
		
		request.getRequestDispatcher("Recipe_Inquiry_Modify.jsp").forward(request, response);
	}
}
