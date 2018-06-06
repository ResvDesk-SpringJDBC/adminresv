<!-- Header starts --> 
	<div class="top-line">
		<div class="top-link">
			Welcome: ${homeBean.adminInfoTO.username} | Logged in as
			${homeBean.adminInfoTO.accessLevel}<br> Last Log-in IP:
			${homeBean.adminInfoTO.lastLoginIP} @ ${homeBean.adminInfoTO.lastLoginDateTime}
		</div>
	</div>
	<div id="logo"><a href="javascript:donothing();"><img  src="static/images/logo.png" height="60"></a></div>

	<div class="right_bar">
		<a href="log-out.html" class="logout">Logout</a> 
		<a href="support.html" class="support">Support</a>
	</div>
	<div class="banner"><center>${homeBean.adminInfoTO.clientTO.client_name}</center></div>
 
<!-- Header ends -->