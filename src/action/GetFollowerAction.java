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
import dto.UserFollowerDto;

public class GetFollowerAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 현재 로그인중인 ID 받아오기
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		// ajax로부터 follower창 페이지값 받아오기
		int follower_page = Integer.parseInt(request.getParameter("follower_page"));
		UserFollowDao uDao = new UserFollowDao();
		ArrayList<UserFollowerDto> followerList = uDao.getMemberFollowerById(loginId, follower_page);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// 리스트를 json배열에 추가
		JSONArray array = new JSONArray();
		for(UserFollowerDto followerDto : followerList) {
			JSONObject obj = new JSONObject();
			obj.put("followerUid", followerDto.getFollowerUid());
			obj.put("followerNickname", followerDto.getFollowerNickname());
			obj.put("followerProfileImage", followerDto.getFollowerProfileImage());
			obj.put("isFollowing", followerDto.getIsFollowing());
			array.add(obj);
		}
		out.print(array);
	}
}
