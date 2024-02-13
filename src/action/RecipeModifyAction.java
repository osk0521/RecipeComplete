package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProfileDao;
import dao.RecipeBoardDao;
import dto.ProfileDto;
//import vo.BoardVo;
import vo.RecipeIngrediVo;
import vo.RecipeProcessVo;

public class RecipeModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 세션 객체 생성
		HttpSession session = request.getSession();
		// 세션에 로그인 아이디가 있다면 가져오기
		String loginId = (String)session.getAttribute("loginId"); 

		if(loginId != null) {
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}

		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
		String u_id = request.getParameter("u_id");
		String title = request.getParameter("title");
		String introduce = request.getParameter("introduce");
		int what = 0;
		int kind = 0;
		int situation = 0;
		int serving = 0;
		int time = 0;
		int lv = 0;
		String thumbnail = null;
		String video = null;
		String tag = null;
		String saveType = null;
		//u_id = "SK";
		System.out.println("u_id : "+u_id+" title : " + title );
		if(request.getParameter("introduce") != null) {
			introduce = request.getParameter("introduce");
		}
		if(request.getParameter("what") != null) {
			what = Integer.parseInt(request.getParameter("what"));
		}
		if(request.getParameter("kind") != null) {
			kind = Integer.parseInt(request.getParameter("kind"));
		}
		if(request.getParameter("situation") != null) {
			situation = Integer.parseInt(request.getParameter("situation"));
		}
		if(request.getParameter("serving") != null) {
			serving = Integer.parseInt(request.getParameter("serving"));
		}
		if(request.getParameter("time") != null) {
			time = Integer.parseInt(request.getParameter("time"));
		}
		if(request.getParameter("lv") != null) {
			lv = Integer.parseInt(request.getParameter("lv"));
		}
		if(request.getParameter("thumbnail") != null) {
			thumbnail = request.getParameter("thumbnail");
		}
		if(request.getParameter("video") != null) {
			video = request.getParameter("video");
		}
		if(request.getParameter("tag") != null) {
			tag = request.getParameter("tag");
		}
		if(request.getParameter("save_type")!=null) {
			saveType = request.getParameter("save_type");
		}
		int complete = 0;
		if(saveType.equals("save_and_reveal")) {
			complete = 1;
		}
		//재료
		ArrayList<RecipeIngrediVo> ingrediList = new ArrayList<RecipeIngrediVo>();
		ArrayList<RecipeProcessVo> stepList = new ArrayList<RecipeProcessVo>();

		int materialCount = 0;
		Enumeration<String> parameterNamesForMaterial = request.getParameterNames();
		while (parameterNamesForMaterial.hasMoreElements()) {
			String paramName = parameterNamesForMaterial.nextElement();
			if (paramName.startsWith("material_nm_")) {
				materialCount++;
			}
		}
		
		for(int i =1; i<= materialCount; i++) {
			for(int j = 1; j<=i; j++){
				if(request.getParameter("material_nm_"+i+"_"+j) != null) {
					int bundleNum = i;
					String bundleName = request.getParameter("material_group_title_"+i);
					String ingrideName = request.getParameter("material_nm_"+i+"_"+j);
					String qty = request.getParameter("material_amt_"+i+"_"+j);
					//System.out.println("재료 번호"+bundleNum+"재료 목록: "+bundleName+ " 재료 이름:"+ingrideName+" 재료 이름:"+qty);
					ingrediList.add(new RecipeIngrediVo(bundleNum, bundleName, ingrideName, qty));
				} else {
					break;
				}
			}
		}
		
		//조리순서
		Enumeration<String> parameterNames = request.getParameterNames();
		int step = 0;
		int imgCnt = 0;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if (paramName.startsWith("process_text_")) {
				step++;
			}
			if (paramName.startsWith("material_nm_")) {
				imgCnt++;
			}
		}
		String lastimage = null;
		int processID = 0;
		String lastTipCaution = null;
		String processText = null;
		String processImage = null;
		String stepMaterial = null;
		String cookEquipment = null;
		String fire = null;
		String tip = null;
		for(int i =1; i<= step; i++) {
			if((request.getParameter("process_text_"+i) != null)) {
				processID = i;
				processText = request.getParameter("process_text_"+i);
				if(processImage!=null) processImage = request.getParameter("process_image_"+i);
				if(stepMaterial!=null) stepMaterial = request.getParameter("step_material_"+i);
				if(cookEquipment!=null) cookEquipment = request.getParameter("step_cooker_"+i);
				if(fire!=null) fire = request.getParameter("step_fire_"+i);
				if(tip!=null) tip = request.getParameter("step_tip_"+i);
				//System.out.println("processText: "+processText+ " processImage:"+processImage+" stepMaterial: "+stepMaterial+" cookEquipment: "+cookEquipment+" fire: "+fire+" tip: "+tip);				
				stepList.add(new RecipeProcessVo(processID, processText, stepMaterial, cookEquipment, fire, tip, processImage, lastimage, lastTipCaution));
			} else {
				break;
			}
		}
		lastTipCaution = request.getParameter("tip_caution");
		if(0<imgCnt) {
			for(int i =1; i<= imgCnt; i++) {
				lastimage += request.getParameter("step_material_"+i);
				if(i<imgCnt) {
					lastimage += "  ///  ";
				}
			}
			//System.out.println("마지막 요리 팁"+lastTipCaution);
			stepList.add(new RecipeProcessVo(10000, null, null, null, null, null, null, lastimage, lastTipCaution));
		}
		if(tag!=null) {
			String [] tagSplit = tag.split(" ");
			tag = "";
			for(int i=0; i<=tagSplit.length-1; i++) {
				if(tagSplit.length<2) {
					tag+="#"+tagSplit[i];
				} else {
					tag+="#"+tagSplit[i]+"/// ";
				}
			}
		}
		RecipeBoardDao bDao = new RecipeBoardDao();
		bDao.updateRecipeInfo(recipeID, u_id, title, introduce, what, kind, situation, serving, time, lv, thumbnail, video, tag, complete);
		System.out.println(u_id +", "+ title +", "+ introduce +", "+ what +", "+ kind +", "+ situation +", "+ serving +", "+ time +", "+ lv +", "+ thumbnail +", "+ video +", "+ tag +", "+ complete);
		bDao.updateRecipeIngredi(recipeID, ingrediList);
		bDao.updateRecipeStepList(recipeID, stepList);
		request.setAttribute("msg", "수정되었습니다.");
		if(complete == 1) {
			request.getRequestDispatcher("Controller?command=view_recipe&recipeID="+recipeID).forward(request, response);
		} else {
			request.getRequestDispatcher("Controller?command=mypage_recipe_view").forward(request, response);
		}
	}

}
