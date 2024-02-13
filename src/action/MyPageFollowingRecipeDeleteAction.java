package action;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyPageFollowingRecipeDao;

public class MyPageFollowingRecipeDeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId"); // 로그인 계정 ID 가져오기
		// 삭제할 팔로잉레시피 목록 받아오기(같은 value가 2개씩 들어옴)
		String[] recipeIdArr = request.getParameterValues("followrecipe_checkbox");
		System.out.println(Arrays.toString(recipeIdArr));
		// 중복값 제거를 위한 HashSet객체 생성
		HashSet<Integer> recipeIdSet = new HashSet<Integer>(); 
		// 생성한 HashSet객체에 중복값을 제거한 recipeId값을 추가
		for(String recipeId : recipeIdArr) {
			recipeIdSet.add(Integer.parseInt(recipeId));
		}
		System.out.println(recipeIdSet.toString());
		// 팔로잉레시피 삭제를 위한 Dao객체 생성
		MyPageFollowingRecipeDao myFollowingRecipeDao = MyPageFollowingRecipeDao.getMyFollowingRecipeDao();
		// 팔로잉레시피 삭제
		for(int recipeId : recipeIdSet) {
			myFollowingRecipeDao.deleteFollowingRecipe(loginId, recipeId);
		}
		request.getRequestDispatcher("Controller?command=mypage_followingrecipe_view").forward(request, response);
	}
}
