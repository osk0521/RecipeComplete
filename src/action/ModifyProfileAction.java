package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ModifyProfileAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 경로
		// path : "uploadProfileImage/"
		ServletContext application = request.getServletContext();
		String path = application.getRealPath("uploadProfileImage"); // 서버의 upload폴더의 절대경로
		String contextPath = application.getContextPath();
		System.out.println("(참고) real path : " + path);
		System.out.println("contextPath : " + contextPath);
		
		File filePath = new File(path);
		if(!filePath.exists()) { // upload 폴더가 없으면
			filePath.mkdirs(); // 폴더를 만듦. make directory
		}
		
		int sizeLimit = 100*1024*1024; // 파일 크기 제한 설정 : 100MB
		
		MultipartRequest multi = new MultipartRequest(
				request,	// 요청 객체
				path,		// 파일 저장 경로(절대경로)
				sizeLimit,	// 파일 최대 크기
				"UTF-8",	// 파일명 한글깨짐 방지
				new DefaultFileRenamePolicy() // 기본적인 'rename 정책'. 파일명이 중복되면 뒤에 숫자 1 2 3 4 ... 붙임
				); // MultipartRequest 객체가 생성될 때! 파일 저장 완료
		
		// 이제, 저장된 파일에 대한 정보(파일이름)를 MultipartRequest 객체로부터 뽑아냄
		Enumeration<?> files = multi.getFileNames();
		String fileObject = (String)files.nextElement();
		String fileName = multi.getFilesystemName(fileObject); // 서버에 저장된 파일 이름을 받아옴
		// (참고) multi.getOriginalFileName(fileObject) -> 웹브라우저에서 선택한 원본 파일 이름을 받아옴
		// (참고) multi.getFile(fileObject).length() -> 업로드한 파일 크기를 받아옴
		String selfIntroduce = multi.getParameter("self_intro");
		
		System.out.println("사진 파일명 : " + fileName);
		System.out.println("자기소개 : " + selfIntroduce);
	}
}
