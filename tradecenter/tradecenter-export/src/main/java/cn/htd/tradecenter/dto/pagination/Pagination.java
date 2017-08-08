package cn.htd.tradecenter.dto.pagination;

/**
 * <p>
 * Summary.
 * </p>
 * <p>
 * Detailed information.
 * </p>
 * <p>
 * Note: Giving proper comments in your program makes it more user friendly and it is assumed as a high quality code.
 * </p>
 *
 * @author zf.zhang
 * @version 1.0.0
 * @since 2016年1月12日
 */
public class Pagination
{

	public static int DEFAULT_PAGE_SIZE = 1000;

	private int pageSize = DEFAULT_PAGE_SIZE;// 每页显示数

	private int currentPage = 1;// 当前页数

	public int totalPages;// 总页数

	private int totalRows;// 总数据数

	//    private int startNum;// 开始记录

	private int nextPage;// 下一页

	private int previousPage;// 上一页

	private boolean hasNextPage;// 是否有下一页

	private boolean hasPreviousPage;// 是否有前一页


	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(final int pageSize)
	{
		this.pageSize = pageSize;
	}


	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(final int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getTotalPages()
	{
		if (getTotalRows() % getPageSize() == 0)
		{
			return getTotalRows() / getPageSize();
		}
		else
		{
			return getTotalRows() / getPageSize() + 1;
		}
	}

	public int getTotalRows()
	{
		return totalRows;
	}

	public void setTotalRows(final int totalRows)
	{
		this.totalRows = totalRows;
	}

	public int getStartNum()
	{
		return (currentPage - 1) * pageSize;
	}

	public int getNextPage()
	{
		if (getCurrentPage() >= getTotalPages())
		{
			hasNextPage = false;
			nextPage = getCurrentPage();
		}
		else
		{
			hasNextPage = true;
			nextPage = getCurrentPage() + 1;
		}
		return nextPage;
	}

	public int getPreviousPage()
	{
		if (getCurrentPage() <= 1)
		{
			hasPreviousPage = false;
			previousPage = 1;
		}
		else
		{
			hasPreviousPage = true;
			previousPage = getCurrentPage() - 1;
		}
		return previousPage;
	}

	public boolean isHasNextPage()
	{
		return hasNextPage;
	}

	public boolean isHasPreviousPage()
	{
		return hasPreviousPage;
	}

}
