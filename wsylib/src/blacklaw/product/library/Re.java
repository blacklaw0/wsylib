package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/

/*
 * Re resource 类,和系统的R类类似,主要是为了方便定义文本常量用的!
 */
public final class Re {
	public final static class integer{
		public final static int loading_time=500;
		public final static int exit_last_time=1000;
		public final static int default_tools_count=2;
		public final static int timeout_millis=7000;
		
	}
	public final static class string{
		public final static String financial_share_body_temple=
				"财经状况\r\n" +
				"普通借阅押金: CW\r\n" +
				"专项借阅押金: CZ\r\n" +
				"现预付款余额: YY\r\n" +
				"普通外借余款: FW\r\n" +
				"专项外借余款: FZ\r\n";
		public final static String financial_share_head="晒晒我图书馆的账单吧!\r\n";
		public final static String remoteConnectTimeout="#error#timeout";
		//public final static String remoteConnectFailure="";


	}

	public final static class leading{
		public final static String sd_data_write="记录已成功写入到~filename~中了";
		public final static String exit="Qing,再按一次就可以退出了!";
		public final static String[] history_pic_save_progress={"正在储存图像...","正在写入文件SDCard~filename~"};
		public final static String[] login_loading_progress={"Loading...","Please wait..."};
		public final static String[] find_loading_progress={"Loading...","Please wait..."};
		public final static String login_success= "Login in success";
		public final static String login_failure= "Login in failure";
		public final static String bookmark_add_success="~title~ \n书签添加成功!";
		public final static String tools_refush_success="小工具(s)更新成功!";
		public final static String news_refush_success="校园新闻更新成功!";
		public final static String hots_refush_success="热词更新成功!";
		public final static class block_login_success{
			public final static String renew="续借  数据读取成功";
			public final static String history="历史  数据读取成功";
			public final static String financial="财经  数据读取成功";
		}
	}
	public final static class url{
		public final static String about="file:///android_asset/about.html";
		public final static String web_error="file:///android_asset/weberror.html";
		public final static String lib_open_time="file:///android_asset/libWhen.html";
		public final static String lib_apartment_local="file:///android_asset/libWhere.html";
		//apk下载路径
		static final public String DOWNLOAD_ADDR="http://download.townke.com/app/wsylib.apk";
		//登陆信息获取路径
		static final public String USER_CONTENT="http://townke.com/app/wsylib/USER_CONTENT.php";
		//搜索页面获取路径
		static final public String BOOKS_FIND="http://townke.com/app/wsylib/BOOKS_FIND.php";
		//热词获取路径
		static final public String HOT_BOOKS="http://townke.com/app/wsylib/HOT_BOOKS.php";
		//学校新闻获取路径
		static final public String SCHOOL_NEWS="http://townke.com/app/wsylib/SCHOOL_NEWS.php";
		//小工具更新地址
		static final public String TOOLS_UPDATA="http://townke.com/app/wsylib/TOOLS_UPDATA.php";
		
	}
	public final static class warning{
		public final static String debug="State:Test program";
		public final static String no_find_input= "亲,不能为空哦!";
		public final static String no_internet="网络连接失败,请确保网络畅通!";
		public final static String no_sdcard="Qing,内存卡好像没有装载耶!";
		public final static String no_register="亲,账户错误哦,在图书馆注册过吗?";
		public final static String no_net_server="亲,网络连接错误耶\n也有可能是学校把服务器关了!";
		public final static String login_input_error="用户名和密码均不能为空";
		public final static String first_login="初次使用请在Login页面登录哦!Qing!";
		public final static String old_data="该页面的数据是\n~lastDataDate~\n获取的旧数据\n请在 Login 页面重新获取";
		public final static String bookmark_readd="~title~ \n书签已存在,请不要重复添加!";
		public final static String no_this_keyword="没有找到相关的条目,换个关键词试试吧!";
		public final static String connect_timeout="网络繁忙,请稍后再试!";
		


		
	}
	public final static class name{
		public final static String renew_cache="blacklaw_wsylib_mylib_renew";
		public final static String history_cache="blacklaw_wsylib_mylib_history";
		public final static String financial_cache="blacklaw_wsylib_mylib_financial";
		public final static String favorite_cache="blacklaw_wsylib_favorite";
		public final static String news_cache="blacklaw_wsylib_news";
		public final static String share_file="blacklaw_wsylib_share";
		public final static String tool_index_share_block="tool_index";
		public final static String date_share_block="lastDataDate";
		public final static String username_share_block="username";
		public final static String password_share_block="password";
		public final static String financial_save="财经状况 ~time~.txt";
	}
}
