package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.UserFollowDao;
import dto.UserFollowingDto;

public class GetFollowingAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 현재 로그인중인 ID 받아오기
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		// ajax로부터 following창 페이지값 받아오기
		int following_page = Integer.parseInt(request.getParameter("following_page"));
		UserFollowDao uDao = new UserFollowDao();
		ArrayList<UserFollowingDto> followingList = uDao.getMemberFollowingById(loginId, following_page);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// 리스트를 json배열에 추가
		JSONArray array = new JSONArray();
		for(UserFollowingDto followingDto : followingList) {
			JSONObject obj = new JSONObject();
			obj.put("followingUid", followingDto.getFollowingUid());
			obj.put("followingNickname", followingDto.getFollowingNickname());
			obj.put("followingProfileImage", followingDto.getFollowingProfileImage());
			obj.put("isFollowing", followingDto.getIsFollowing());
			array.add(obj);
		}
		out.print(array);
	}
}
