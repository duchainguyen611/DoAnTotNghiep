var selectedSortby;
var tt = 'Thứ tự';
var selectedViewData = "data";
var filter = new Bizweb.SearchFilter()
if(colId > 0 ){
	filter.addValue("collection", "collections", colId, "AND");
}
function awe_showLoading(selector) {
	var loading = $('.loader').html();
	$(selector).addClass("loading").append(loading); 
}  window.awe_showLoading=awe_showLoading;

function awe_showPopup(selector) {
	$(selector).addClass('active');
}  window.awe_showPopup=awe_showPopup;
function awe_hideLoading(selector) {
	$(selector).removeClass("loading"); 
	$(selector + ' .loading-icon').remove();
}  window.awe_hideLoading=awe_hideLoading;
/*******
	# Callback Wishlist
		********************************************************/


function toggleFilter(e) {
	_toggleFilter(e);
	renderFilterdItems();
	doSearch(1);
}
function _toggleFilterdqdt(e) {
	var $element = $(e);
	var group = 'Khoảng giá';
	var field = 'price_min';
	var operator = 'OR';	 
	var value = $element.attr("data-value");	
	filter.deleteValuedqdt(group, field, value, operator);
	filter.addValue(group, field, value, operator);

	renderFilterdItems();
	doSearch(1);
}

function _toggleFilterdqdt2(e) {
	var $element = $(e);
	var group = 'Khoảng giá';
	var field = 'price_min';
	var operator = 'OR';	 
	var value = $element.attr("value");		
	filter.deleteValuedqdt(group, field, value, operator);
	filter.addValue(group, field, value, operator);
	renderFilterdItems();
	doSearch(1);
}
function _toggleFilter(e) {
	var $element = $(e);
	var group = $element.attr("data-group");
	var field = $element.attr("data-field");
	var text = $element.attr("data-text");
	var value = $element.attr("value");
	var operator = $element.attr("data-operator");
	var filterItemId = $element.attr("id");

	if (!$element.is(':checked')) {
		filter.deleteValue(group, field, value, operator);
	}
	else{
		filter.addValue(group, field, value, operator);
	}

	$(".catalog_filters li[data-handle='" + filterItemId + "']").toggleClass("active");
}

function renderFilterdItems() {
	var $container = $(".filter-container__selected-filter-list ul");
	$container.html("");

	$(".filter-container input[type=checkbox]").each(function(index) {
		if ($(this).is(':checked')) {
			var id = $(this).attr("id");
			var name = $(this).closest("label").text();

			addFilteredItem(name, id);
		}
	});

	if($(".filter-container input[type=checkbox]:checked").length > 0)
		$(".filter-container__selected-filter").show();
	else
		$(".filter-container__selected-filter").hide();
}

function addFilteredItem(name, id) {
	var filteredItemTemplate = "<li class='filter-container__selected-filter-item' for='{3}'><a href='javascript:void(0)' onclick=\"{0}\"><i class='fa fa-close'></i> {1}</a></li>";
	filteredItemTemplate = filteredItemTemplate.replace("{0}", "removeFilteredItem('" + id + "')");
	filteredItemTemplate = filteredItemTemplate.replace("{1}", name);
	filteredItemTemplate = filteredItemTemplate.replace("{3}", id);
	var $container = $(".filter-container__selected-filter-list ul");
	$container.append(filteredItemTemplate);
}

function removeFilteredItem(id) {
	$(".filter-container #" + id).trigger("click");
}

function searchShow(html,page,reload){
	var $html = $(html);

	// Muốn thay thẻ DIV nào khi filter thì viết như này
	var $categoryProducts = $($html[0]); 
	var xxx = $categoryProducts.find('.call-count');

	$('.tt span').text(xxx.text());

	$(".category-products").html($categoryProducts.html());

	if(reload == false){
		pushCurrentFilterState({sortby: selectedSortby, page: page});
	}
	awe_hidePopup('.loading');

	$('[data-toggle="tooltip"]').tooltip({container: 'body'}); 
	$('.add_to_cart').click(function(e){
		e.preventDefault();
		var $this = $(this);						   
		var form = $this.parents('form');						   

		$.ajax({
			type: 'POST',
			url: '/cart/add.js',
			async: false,
			data: form.serialize(),
			dataType: 'json',
			error: addToCartFail,
			beforeSend: function() {  
				if(window.theme_load == "icon"){
					awe_showLoading('.btn-addToCart');
				} else{
					awe_showPopup('.loading');
				}
			},
			success: addToCartSuccess,
			cache: false
		});
	});
	$('html, body').animate({
		scrollTop: $('.category-products').offset().top
	}, 300);
	$('.dosearch').click(function(e){
		var $data = $(this).attr('data-onclick');
		doSearch($data);
	})
	$('.awe_sortby').on('click',function(e){
		var $data = $(this).attr('data-onclick');
		sortby($data);

	})

	resortby(selectedSortby);					   
	//awe_callbackW();
awe_lazyloadImage();
	$('.open-filters').removeClass('open');
	$('.dqdt-sidebar').removeClass('open');
	if(window.BPR)
		return window.BPR.initDomEls(), window.BPR.loadBadges();
}
function clearAllFiltered() {
	filter = new Bizweb.SearchFilter();
	

	 $(".filter-container__selected-filter-list ul").html("");
	  $(".filter-container input[type=checkbox]").attr('checked', false);
	  $(".filter-container__selected-filter").hide();

	  doSearch(1);
	 }

	 function doSearch(page, options) {

		 if(!options) options = {};

		 //NProgress.start();
		 $('.aside.aside-mini-products-list.filter').removeClass('active');
		 awe_showPopup('.loading');
		 filter.search({
			 view: selectedViewData,
			 page: page,
			 sortby: selectedSortby,
			 success: function (html) {
				 searchShow(html,page,false);


			 }
		 });		

	 }

	 function sortby(sort) {			 
		 switch(sort) {
			 case "price-asc":
				 selectedSortby = "price_min:asc";					   
				 break;
			 case "price-desc":
				 selectedSortby = "price_min:desc";
				 break;
			 case "alpha-asc":
				 selectedSortby = "name:asc";
				 break;
			 case "alpha-desc":
				 selectedSortby = "name:desc";
				 break;
			 case "created-desc":
				 selectedSortby = "created_on:desc";
				 break;
			 case "created-asc":
				 selectedSortby = "created_on:asc";
				 break;
			 default:
				 selectedSortby = "";
				 break;
		 }

		 doSearch(1);
	 }

	 function resortby(sort) {
		 switch(sort) {				  
			 case "price_min:asc":
				 tt = "Giá tăng dần";
				 break;
			 case "price_min:desc":
				 tt = "Giá giảm dần";
				 break;
			 case "name:asc":
				 tt = "Tên A → Z";
				 break;
			 case "name:desc":
				 tt = "Tên Z → A";
				 break;
			 case "created_on:desc":
				 tt = "Hàng mới nhất";
				 break;
			 case "created_on:asc":
				 tt = "Hàng cũ nhất";
				 break;
			 default:
				 tt = "Mặc định";
				 break;
		 }			   
		 $('#sort-by > ul > li > span').html(tt);

	 }


	 function _selectSortby(sort) {			 
		 resortby(sort);
		 switch(sort) {
			 case "price-asc":
				 selectedSortby = "price_min:asc";
				 break;
			 case "price-desc":
				 selectedSortby = "price_min:desc";
				 break;
			 case "alpha-asc":
				 selectedSortby = "name:asc";
				 break;
			 case "alpha-desc":
				 selectedSortby = "name:desc";
				 break;
			 case "created-desc":
				 selectedSortby = "created_on:desc";
				 break;
			 case "created-asc":
				 selectedSortby = "created_on:asc";
				 break;
			 default:
				 selectedSortby = sort;
				 break;
		 }
	 }

	 function toggleCheckbox(id) {
		 $(id).click();
	 }

	 function pushCurrentFilterState(options) {

		 if(!options) options = {};
		 var url = filter.buildSearchUrl(options);
		 var queryString = url.slice(url.indexOf('?'));			  
		 if(selectedViewData == 'data_list'){
			 queryString = queryString + '&view=list';				 
		 }
		 else{
			 queryString = queryString + '&view=grid';				   
		 }

		 pushState(queryString);
	 }

	 function pushState(url) {
		 window.history.pushState({
			 turbolinks: true,
			 url: url
		 }, null, url)
	 }
	 function switchView(view) {			  
		 switch(view) {
			 case "list":
				 selectedViewData = "data_list";					   
				 break;
			 default:
				 selectedViewData = "data";

				 break;
		 }			   
		 doSearch(1);
	 }

	 function selectFilterByCurrentQuery() {

		 var isFilter = false;
		 var url = window.location.href;
		 var queryString = decodeURI(window.location.search);

		 var filters = queryString.match(/\(.*?\)/g);
		 var url = '/search' + queryString;


		 if(queryString) {
			 isFilter = true;
		 }
		 if(filters && filters.length > 0) {

			 filters.forEach(function(item) {
				 item = item.replace(/\(\(/g, "(");
				 var element = $(".filter-container input[value='" + item + "']");
				 element.attr("checked", "checked");
				 _toggleFilter(element);
			 });
			 isFilter = true;
		 }

		 var sortOrder = getParameter(url, "sortby");
		 if(sortOrder) {
			 _selectSortby(sortOrder);
		 }
		 var page = getParameter(url, "page");

		 if(isFilter) {
			 url = url.replace('view=grid','view=data');
			 $.ajax({
				 url: url,
				 dataType: 'html',
				 success: function (html) {
					 searchShow(html,page,true);

				 }
			 });

		 }
	 }
	 function getParameter(url, name) {
		 name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		 var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			 results = regex.exec(url);
		 return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	 }

	 $( document ).ready(function() {

		 selectFilterByCurrentQuery();
		 $('.filter-group .filter-group-title').click(function(e){
			 $(this).parent().toggleClass('active');
		 });

		 $('.filter-mobile').click(function(e){
			 $('.aside.aside-mini-products-list.filter').toggleClass('active');
		 });

		 $('#show-admin-bar').click(function(e){
			 $('.aside.aside-mini-products-list.filter').toggleClass('active');
		 });

		 $('.filter-container__selected-filter-header-title').click(function(e){
			 $('.aside.aside-mini-products-list.filter').toggleClass('active');
		 });
	 });

	 
	 
	 var maxx = 10000000/10;
	 $('#slider').slider({
		 min: '0',
		 max: maxx,
		 range: true,
		 values: [0,10000000],
		 slide: function(event, ui) {
			 if(ui.values[0] >= ui.values[1]) {
				 if(ui.handle == $("#slider a")[0]) {
					 $("#slider").slider("values", 1, ui.value);
					 ui.values[0] = ui.value;
					 ui.values[1] = ui.value;
				 } else {
					 $("#slider").slider("values", 0, ui.value);
					 ui.values[0] = ui.value;
					 ui.values[1] = ui.value;
				 }
			 }			

			 var uimax =ui.values[1]+1;	
			 	
			 if(ui.values[0] > 0){
				 var v1 = Bizweb.formatMoney(ui.values[0], "{{amount_no_decimals_with_comma_separator}}₫");
			 }else{
				 var v1 = ui.values[0];
			 }
			  var v2 = Bizweb.formatMoney((ui.values[1]+1), "{{amount_no_decimals_with_comma_separator}}₫");

			  

			  $('#start input').val(v1);
			   $('#stop input').val(v2);
			   var uimin =ui.values[0]-1;
			   var uimax =ui.values[1]+2;
			   $('#filter-value').attr('data-value','(>'+uimin+' AND <'+uimax+')');
			  }
			 });
			 $(document).on('change','#start',function(e){
				 var val = parseInt($('#start input').val())-1;
				 var val2 = parseInt($('#stop input').val())+1;

				 $("#slider").slider("values",0,parseInt(val));
				 $('#filter-value').attr('data-value','(>'+val+' AND <'+val2+')');
			 });
			 $(document).on('change','#stop',function(e){
				 var val = parseInt($('#start input').val())-1;
				 var val2 = parseInt($('#stop input').val())+1;

				 $("#slider").slider("values",1,parseInt(val2));
				 $('#filter-value').attr('data-value','(>'+val+' AND <'+val2+')');
			 });
			 
			 

			 $('.filter-item--check-box input').change(function(e){
				 var $this = $(this);
				 toggleFilter($this);
			 })
			 $('a#filter-value').click(function(e){
				 var $this = $(this);
				 _toggleFilterdqdt($this);
			 })
			 $('.dosearch').click(function(e){
				 var $data = $(this).attr('data-onclick');
				 doSearch($data);
			 })
			 $('.awe_sortby').on('click',function(e){
				 var $data = $(this).attr('data-onclick');
				 sortby($data);

			 })

			 function addToCartFail(jqXHR, textStatus, errorThrown){
				 var response = $.parseJSON(jqXHR.responseText);
				 var $info = '<div class="error">'+ response.description +'</div>';
			 }
			  function addToCartSuccess (jqXHR, textStatus, errorThrown){

				  $.ajax({
					  type: 'GET',
					  url: '/cart.js',
					  async: false,
					  cache: false,
					  dataType: 'json',
					  success: function (cart){
						  awe_hidePopup('.loading');

						  $('#popupCartModal').html('');		
						  var src = Bizweb.resizeImage(jqXHR['image'], 'small');
						  if(jqXHR['image'] == null){
							  var src = "//bizweb.dktcdn.net/thumb/1024x1024/assets/themes_support/noimage.gif";
						  }
						  var $popupMobile = '<div class="popup_overlay"></div>'
						  +'<div class="modal-dialog">'

						  +'<div class="modal-content">'
						  + '<button type="button" class="close" data-dismiss="modal" data-backdrop="false" aria-label="Close" style="position: relative; z-index: 9;"><span aria-hidden="true">×</span></button>'
						  + '<div class="row row-noGutter"><div class="modal-left col-sm-6">'
						  + '<h3 class="title"><i class="fa fa-check"></i> Sản phẩm vừa thêm vào giỏ hàng</h3>'

						  + '<div class="modal-body"><div class="media"><div class="media-left"><div class="thumb-1x1">'
						  + '<img width="70px" src="'+ src +'" alt="'+ jqXHR['title'] +'"></div></div>'
						  + '<div class="media-body"><div class="product-title">'+ jqXHR['name'] +'</div>'
						  + '<div class="product-new-price"><span>' + (jqXHR['price']).formatMoney(0) + ' đ</span></div></div></div>'
						  + '</div>'
						  + '</div>'
						  + '<div class="modal-right col-sm-6">'
						  + '<h3 class="title"><a href="/cart"> Giỏ hàng của bạn <i>(<b class="cart-popup-count"></b> sản phẩm)</i> </a></h3>'
						  + '<div class="total_price"><span>Tổng tiền: </span> <span class="">' + (cart.total_price).formatMoney(0) + '</span></div>'
						  + '<a href="/checkout" class="btn btn-block btn-red">Tiến hành thanh toán</a>'
						  + '</div>'
						  + '</div></div>';
						  $('#popupCartModal').html($popupMobile);
						  $('#popupCartModal').modal(); 	
						  Bizweb.updateCartFromForm(cart, '.top-cart-content .mini-products-list');
						  Bizweb.updateCartPopupForm(cart, '#popup-cart-desktop .tbody-popup');
					  }
				  });







			  }