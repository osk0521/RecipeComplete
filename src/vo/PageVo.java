package vo;

public class PageVo {
	private int currentPage;
	private int startPage;
	private int endPage;
	private int lastPage;
	
	public PageVo(int currentPage, int startPage, int endPage, int lastPage) {
		this.currentPage = currentPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.lastPage = lastPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getLastPage() {
		return lastPage;
	}
	
	
}
