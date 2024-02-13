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
import vo.RecipeIngrediVo;
import vo.RecipeProcessVo;

public class RecipeWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId"); 
		// 세션에 로그인 아이디가 있다면 가져오기

		if(loginId != null) {
			ProfileDao profileDao = new ProfileDao(); // 유저 프로필 메서드 관련 DAO
			ProfileDto myProfile = profileDao.getProfileById(loginId); // 로그인한 유저의 프로필 정보 저장
			request.setAttribute("myProfile", myProfile);
		}
		int recipeID = Integer.parseInt(request.getParameter("recipeID"));
		String title = request.getParameter("title");
		String introduce = request.getParameter("introduce");
		int what = 9;
		int kind = 7;
		int situation = 9;
		int serving = 0;
		int time = 0;
		int lv = 0;
		String thumbnail = request.getParameter("thumbnail");
		String video = request.getParameter("video_link");
		String tag = null;
		String saveType = null;
		//loginId = "admin";
		
		if(!(request.getParameter("what").equals("0"))) {
			what = Integer.parseInt(request.getParameter("what"));
		}
		if(!(request.getParameter("kind").equals("0"))) {
			kind = Integer.parseInt(request.getParameter("kind"));
		}
		if(!(request.getParameter("situation").equals("0"))) {
			System.out.println(request.getParameter("situation"));
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
		if(request.getParameter("tags") != null && !(request.getParameter("tags").equals(""))) {
			System.out.println(request.getParameter("tags"));
			tag = request.getParameter("tags");
			String [] tagSplit = tag.split(" ");
			tag = "";
			for(int i=0; i<=tagSplit.length-1; i++) {
				if(tagSplit.length<2) {
					tag+="#"+tagSplit[i];
				} else {
					if(i < tagSplit.length-1) {
						tag+="#"+tagSplit[i]+" /// ";
					} else {
						tag+="#"+tagSplit[i];
					}
				}
			}
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
		RecipeBoardDao bDao = new RecipeBoardDao();
		bDao.insertRecipeInfo(recipeID, loginId, title, introduce, what, kind, situation, serving, time, lv, thumbnail, video, tag, complete);
		bDao.insertRecipeIngredi(recipeID, ingrediList);
		bDao.insertRecipeSteps(recipeID, stepList);
		if(complete == 1) {
			request.getRequestDispatcher("Controller?command=view_recipe"+recipeID).forward(request, response);
		} else {
			request.getRequestDispatcher("Controller?command=mypage_recipe_view").forward(request, response);
		}//추후 변경
		
	}
}
