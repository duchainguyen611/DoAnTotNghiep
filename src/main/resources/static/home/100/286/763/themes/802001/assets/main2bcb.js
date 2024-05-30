$(document).ready(function ($) {
	"use strict";
	awe_owl();
	awe_backtotop();		
	awe_category();
	awe_menumobile();
	awe_tab();		
	awe_lazyloadImage();

});

/********************************************************
	# LazyLoad
	********************************************************/
function awe_lazyloadImage() {
var ll = new LazyLoad({
		elements_selector: ".lazyload",
		load_delay: 500,
		threshold: 0
	});

} window.awe_lazyloadImage=awe_lazyloadImage;

/********************************************************
	# convertVietnamese
	********************************************************/
function awe_convertVietnamese(str) { 
	str= str.toLowerCase();
	str= str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
	str= str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
	str= str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
	str= str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
	str= str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
	str= str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
	str= str.replace(/đ/g,"d"); 
	str= str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'| |\"|\&|\#|\[|\]|~|$|_/g,"-");
	str= str.replace(/-+-/g,"-");
	str= str.replace(/^\-+|\-+$/g,""); 

	return str; 
} window.awe_convertVietnamese=awe_convertVietnamese;
/********************************************************
	# SHOW NOITICE
	********************************************************/
function awe_showNoitice(selector) {
	$(selector).animate({right: '0'}, 500);
	setTimeout(function() {
		$(selector).animate({right: '-300px'}, 500);
	}, 3500);
}  window.awe_showNoitice=awe_showNoitice;

/********************************************************
	# SHOW LOADING
	********************************************************/
function awe_showLoading(selector) {
	var loading = $('.loader').html();
	$(selector).addClass("loading").append(loading); 
}  window.awe_showLoading=awe_showLoading;

/********************************************************
	# HIDE LOADING
	********************************************************/
function awe_hideLoading(selector) {
	$(selector).removeClass("loading"); 
	$(selector + ' .loading-icon').remove();
}  window.awe_hideLoading=awe_hideLoading;

/********************************************************
	# SHOW POPUP
	********************************************************/
function awe_showPopup(selector) {
	$(selector).addClass('active');
}  window.awe_showPopup=awe_showPopup;

/********************************************************
	# HIDE POPUP
	********************************************************/
function awe_hidePopup(selector) {
	$(selector).removeClass('active');
}  window.awe_hidePopup=awe_hidePopup;

/********************************************************
	# CONVERT VIETNAMESE
	********************************************************/
function awe_convertVietnamese(str) { 
	str= str.toLowerCase();
	str= str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
	str= str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
	str= str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
	str= str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
	str= str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
	str= str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
	str= str.replace(/đ/g,"d"); 
	str= str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'| |\"|\&|\#|\[|\]|~|$|_/g,"-");
	str= str.replace(/-+-/g,"-");
	str= str.replace(/^\-+|\-+$/g,""); 
	return str; 
} window.awe_convertVietnamese=awe_convertVietnamese;


/********************************************************
	# SIDEBAR CATEOGRY
	********************************************************/
function awe_category(){
	$('.nav-category .fa-angle-down').click(function(e){
		$(this).parent().toggleClass('active');
	});
} window.awe_category=awe_category;

/********************************************************
	# MENU MOBILE
	********************************************************/
function awe_menumobile(){
	$('.menu-bar').click(function(e){
		e.preventDefault();
		$('#nav').toggleClass('open');
	});
	$('#nav .fa').click(function(e){		
		e.preventDefault();
		$(this).parent().parent().toggleClass('open');
	});
} window.awe_menumobile=awe_menumobile;

/********************************************************
	# ACCORDION
	********************************************************/
function awe_accordion(){
	$('.accordion .nav-link').click(function(e){
		e.preventDefault;
		$(this).parent().toggleClass('active');
	})
} window.awe_accordion=awe_accordion;

/********************************************************
	# OWL CAROUSEL
	********************************************************/
function awe_owl() { 
	$('.owl-carousel:not(.not-aweowl)').each( function(){
		var xs_item = $(this).attr('data-xs-items');
		var sm_item = $(this).attr('data-sm-items');		
		var md_item = $(this).attr('data-md-items');
		var lg_item = $(this).attr('data-lg-items');
		var xl_item = $(this).attr('data-xl-items');
		var margin=$(this).attr('data-margin');
		var dot=$(this).attr('data-dot');
		var nav=$(this).attr('data-nav');
		if (typeof margin !== typeof undefined && margin !== false) {    
		} else{
			margin = 30;
		}
		if (typeof xs_item !== typeof undefined && xs_item !== false) {    
		} else{
			xs_item = 1;
		}
		if (typeof sm_item !== typeof undefined && sm_item !== false) {    
		} else{
			sm_item = 1;
		}
		if (typeof md_item !== typeof undefined && md_item !== false) {    

		} else{
			md_item = 3;
		}	
		if (typeof lg_item !== typeof undefined && lg_item !== false) {    
		} else{
			lg_item = 3;
		}
		if (typeof xl_item !== typeof undefined && xl_item !== false) {    
		} else{
			xl_item = 4;
		}
		if (typeof dot !== typeof undefined && dot !== true) {   
			dot = dot;
		} else{
			dot = false;
		}
		if (typeof nav !== typeof undefined && nav !== true) {   
			nav = nav;
		} else{
			nav = false;
		}
		$(this).owlCarousel({
			loop:false,
			margin:Number(margin),
			responsiveClass:true,
			dots:dot,
			nav:nav,
			responsive:{
				0:{
					items:Number(xs_item)				
				},
				576:{
					items:Number(sm_item)				
				},
				768:{
					items:Number(md_item)				
				},
				992:{
					items:Number(lg_item)				
				},
				1200:{
					items:Number(xl_item)				
				}
			}
		})
	})
} window.awe_owl=awe_owl;


/********************************************************
	# BACKTOTOP
	********************************************************/
function awe_backtotop() { 
	if ($('.back-to-top').length) {
		var scrollTrigger = 100, // px
			backToTop = function () {
				var scrollTop = $(window).scrollTop();
				if (scrollTop > scrollTrigger) {
					$('.back-to-top').addClass('show');
				} else {
					$('.back-to-top').removeClass('show');
				}


			};
		backToTop();
		$(window).on('scroll', function () {
			backToTop();
		});
		$('.back-to-top').on('click', function (e) {
			e.preventDefault();
			$('html,body').animate({
				scrollTop: 0
			}, 700);
		});
	}
} window.awe_backtotop=awe_backtotop;

/********************************************************
	# TAB
	********************************************************/
function awe_tab() {
	$(".e-tabs:not(.not-dqtab)").each( function(){
		$(this).find('.tabs-title li:first-child').addClass('current');
		$(this).find('.tab-content').first().addClass('current');

		$(this).find('.tabs-title li').click(function(){
			var tab_id = $(this).attr('data-tab');
			var url = $(this).attr('data-url');
			$(this).closest('.e-tabs').find('.tab-viewall').attr('href',url);
			$(this).closest('.e-tabs').find('.tabs-title li').removeClass('current');
			$(this).closest('.e-tabs').find('.tab-content').removeClass('current');
			$(this).addClass('current');
			$(this).closest('.e-tabs').find("#"+tab_id).addClass('current');
		});    
	});
} window.awe_tab=awe_tab;

/********************************************************
	# Callback Wishlist
	
function awe_callbackW() {
	iWishCheck();				  
	iWishCheckInCollection();
	$(".iWishAdd").click(function () {			
		var iWishvId = iWish$(this).parents('form').find("[name='id']").val();
		if (typeof iWishvId === 'undefined') {
			iWishvId = iWish$(this).parents('form').find("[name='variantId']").val();
		};
		var iWishpId = iWish$(this).attr('data-product');
		if (Bizweb.template == 'collection' || Bizweb.template == 'index') {
			iWishvId = iWish$(this).attr('data-variant');
		}
		if (typeof iWishvId === 'undefined' || typeof iWishpId === 'undefined') {
			return false;
		}
		if (iwish_cid == 0) {
			iWishGotoStoreLogin();
		} else {
			var postObj = {
				actionx : 'add',
				cust: iwish_cid,
				pid: iWishpId,
				vid: iWishvId
			};
			iWish$.post(iWishLink, postObj, function (data) {
				if (iWishFindAndGetVal('#iwish_post_result', data) == undefined) return;
				var result = (iWishFindAndGetVal('#iwish_post_result', data).toString().toLowerCase() === 'true');
				var redirect = parseInt(iWishFindAndGetVal('#iwish_post_redirect', data), 10);
				if (result) {
					if (Bizweb.template == "product") {
						iWish$('.iWishAdd').addClass('iWishHidden'), iWish$('.iWishAdded').removeClass('iWishHidden');
						if (redirect == 2) {
							iWishSubmit(iWishLink, { cust: iwish_cid });
						}
					}
					else if (Bizweb.template == 'collection' || Bizweb.template == 'index') {
						iWish$.each(iWish$('.iWishAdd'), function () {
							var _item = $(this);
							if (_item.attr('data-variant') == iWishvId) {
								_item.addClass('iWishHidden'), _item.parent().find('.iWishAdded').removeClass('iWishHidden');
							}
						});
					}
				}
			}, 'html');
		}
		return false;
	});
	$(".iWishAdded").click(function () {
		var iWishvId = iWish$(this).parents('form').find("[name='id']").val();
		if (typeof iWishvId === 'undefined') {
			iWishvId = iWish$(this).parents('form').find("[name='variantId']").val();
		};
		var iWishpId = iWish$(this).attr('data-product');
		if (Bizweb.template == 'collection' || Bizweb.template == 'index') {
			iWishvId = iWish$(this).attr('data-variant');
		}
		if (typeof iWishvId === 'undefined' || typeof iWishpId === 'undefined') {
			return false;
		}
		if (iwish_cid == 0) {
			iWishGotoStoreLogin();
		} else {
			var postObj = {
				actionx: 'remove',
				cust: iwish_cid,
				pid: iWishpId,
				vid: iWishvId
			};
			iWish$.post(iWishLink, postObj, function (data) {
				if (iWishFindAndGetVal('#iwish_post_result', data) == undefined) return;
				var result = (iWishFindAndGetVal('#iwish_post_result', data).toString().toLowerCase() === 'true');
				var redirect = parseInt(iWishFindAndGetVal('#iwish_post_redirect', data), 10);
				if (result) {
					if (Bizweb.template == "product") {
						iWish$('.iWishAdd').removeClass('iWishHidden'), iWish$('.iWishAdded').addClass('iWishHidden');
					}
					else if (Bizweb.template == 'collection' || Bizweb.template == 'index') {
						iWish$.each(iWish$('.iWishAdd'), function () {
							var _item = $(this);
							if (_item.attr('data-variant') == iWishvId) {
								_item.removeClass('iWishHidden'), _item.parent().find('.iWishAdded').addClass('iWishHidden');
							}
						});
					}
				}
			}, 'html');
		}
		return false;
	});

}window.awe_callbackW=awe_callbackW;

********************************************************/


/********************************************************
	# Other
	********************************************************/
$('.dropdown-toggle').click(function() {
	$(this).parent().toggleClass('open'); 	
}); 
$('.btn-close').click(function() {
	$(this).parents('.dropdown').toggleClass('open');
}); 
$('body').click(function(event) {
	if (!$(event.target).closest('.dropdown').length) {
		$('.dropdown').removeClass('open');
	};
});
$(document).on('click','.qtyplus',function(e){
	e.preventDefault();   
	fieldName = $(this).attr('data-field'); 
	var currentVal = parseInt($('input[data-field='+fieldName+']').val());
	if (!isNaN(currentVal)) { 
		$('input[data-field='+fieldName+']').val(currentVal + 1);
	} else {
		$('input[data-field='+fieldName+']').val(0);
	}
});

$(document).on('click','.qtyminus',function(e){
	e.preventDefault(); 
	fieldName = $(this).attr('data-field');
	var currentVal = parseInt($('input[data-field='+fieldName+']').val());
	if (!isNaN(currentVal) && currentVal > 1) {          
		$('input[data-field='+fieldName+']').val(currentVal - 1);
	} else {
		$('input[data-field='+fieldName+']').val(1);
	}
});
$(document).on('click','.popup_overlay',function(e){	
	$('#popupCartModal').modal('hide');
})
jQuery(document).ready(function ($) {
	$('#nav-mobile .fa').click(function(e){		
		e.preventDefault();
		$(this).parent().parent().toggleClass('open');
	});
	$('.menu-bar').click(function(e){
		e.preventDefault();
		$('#nav-mobile').toggleClass('open');
	});

	$('.open-filters').click(function(e){

		$(this).toggleClass('open');
		$('.dqdt-sidebar').toggleClass('open');
	});

	$('.inline-block.account-dr>a').click(function(e){
		if($(window).width() < 992){
			e.preventDefault();

		}
	})

});

$(document).on('click','.overlay, .close-popup, .btn-continue, .fancybox-close', function() {   
	hidePopup('.awe-popup'); 	
	setTimeout(function(){
		$('.loading').removeClass('loaded-content');
	},500);
	return false;
})
$('.button-select .btn').click(function(e){
	$('.button-select .btn').removeClass('active');
	$(this).addClass('active');
	$('.dq-form-gif').attr('data-id-gif',$(this).attr('data-id'));
})
$('footer .footer-widget h3').click(function(e){
	$(this).parent().toggleClass('active');
})

//Deal countdown
$.fn.Dqdt_CountDown = function( options ) {
	return this.each(function() {
		// get instance of the dqdt.
		new  $.Dqdt_CountDown( this, options );
	});
}
$.Dqdt_CountDown = function( obj, options ){

	this.options = $.extend({
		autoStart			: true,
		LeadingZero:true,
		DisplayFormat:"<div><span>%%D%%</span> Days</div><div><span>%%H%%</span> Hours</div><div><span>%%M%%</span> Mins</div><div><span>%%S%%</span> Secs</div>",
		FinishMessage:"Hết hạn",
		CountActive:true,
		TargetDate:null
	}, options || {} );
	if( this.options.TargetDate == null || this.options.TargetDate == '' ){
		return ;
	}
	this.timer  = null;
	this.element = obj;
	this.CountStepper = -1;
	this.CountStepper = Math.ceil(this.CountStepper);
	this.SetTimeOutPeriod = (Math.abs(this.CountStepper)-1)*1000 + 990;
	var dthen = new Date(this.options.TargetDate);
	var dnow = new Date();
	if( this.CountStepper > 0 ) {
		ddiff = new Date(dnow-dthen);
	}
	else {
		ddiff = new Date(dthen-dnow);
	}
	gsecs = Math.floor(ddiff.valueOf()/1000);
	this.CountBack(gsecs, this);

};
$.Dqdt_CountDown.fn =  $.Dqdt_CountDown.prototype;
$.Dqdt_CountDown.fn.extend =  $.Dqdt_CountDown.extend = $.extend;
$.Dqdt_CountDown.fn.extend({
	calculateDate:function( secs, num1, num2 ){
		var s = ((Math.floor(secs/num1))%num2).toString();
		if ( this.options.LeadingZero && s.length < 2) {
			s = "0" + s;
		}
		return "<b>" + s + "</b>";
	},
	CountBack:function( secs, self ){
		if (secs < 0) {
			self.element.innerHTML = '<div class="lof-labelexpired"> '+self.options.FinishMessage+"</div>";
			return;
		}
		clearInterval(self.timer);
		DisplayStr = self.options.DisplayFormat.replace(/%%D%%/g, self.calculateDate( secs,86400,100000) );
		DisplayStr = DisplayStr.replace(/%%H%%/g, self.calculateDate(secs,3600,24));
		DisplayStr = DisplayStr.replace(/%%M%%/g, self.calculateDate(secs,60,60));
		DisplayStr = DisplayStr.replace(/%%S%%/g, self.calculateDate(secs,1,60));
		self.element.innerHTML = DisplayStr;
		if (self.options.CountActive) {
			self.timer = null;
			self.timer =  setTimeout( function(){
				self.CountBack((secs+self.CountStepper),self);
			},( self.SetTimeOutPeriod ) );
		}
	}

});


$(document).ready(function(){
	$('.icon-search-mobile').on('touchstart',function(e){
		$('#nav-mobile').removeClass('open');
	})
	//deal slide    

	$('[data-countdown="countdown"]').each(function(index, el) {
		var $this = $(this);
		var $date = $this.data('date').split("-");
		$this.Dqdt_CountDown({
			TargetDate:$date[0]+"/"+$date[1]+"/"+$date[2]+" "+$date[3]+":"+$date[4]+":"+$date[5],
			DisplayFormat:"<div class=\"countdown-times\"><div class=\"day\"><span>%%D%%</span><br>Ngày</div><div class=\"hours\"><span>%%H%%</span><br>Giờ</div><div class=\"minutes\"><span>%%M%%</span><br>Phút</div><div class=\"seconds\"><span>%%S%%</span><br>Giây</div></div>",
			FinishMessage: "Hết hạn"
		});
	});
});

//tab

//Create tab
$(".not-dqtab").each( function(){
	$(this).find('.tabs-title li:first-child').addClass('current');
	$(this).find('.tab-content').first().addClass('current');

	$(this).find('.tabs-title li').click(function(){
		var tab_id = $(this).attr('data-tab');
		var url = $(this).attr('data-url');
		$(this).closest('.e-tabs').find('.tab-viewall').attr('href',url);
		$(this).closest('.e-tabs').find('.tabs-title li').removeClass('current');
		$(this).closest('.e-tabs').find('.tab-content').removeClass('current');
		$(this).addClass('current');
		$(this).closest('.e-tabs').find("#"+tab_id).addClass('current');
	});
});
//Count tab
var count = 0
$('.tab-content').each(function(e){
	count +=1;
})
$('.not-dqtab .next').click(function(e){
	var str = $('#tab-titlexx').attr('data-tab');
	var res = str.replace("tab-", "");
	res = Number(res);
	if(res < count){
		var current = res + 1;
	}else{
		var current = 1;
	}
	action(current);									
})
$('.not-dqtab .prev').click(function(e){
	var str = $('#tab-titlexx').attr('data-tab');
	var res = str.replace("tab-", "");
	res = Number(res);
	if(res > 1){
		var current = res - 1;
	}else{
		var current = count;
	}
	action(current);									
})
function action(current){								
	$('#tab-titlexx').attr('data-tab','tab-'+current);
	var text = '';
	$('ul.tabs.tabs-title.clearfix.hidden-xs li').each(function(e){
		if($(this).attr('data-tab') == 'tab-'+current){
			title = $(this).find('span').text();
		}
	})
	$('#tab-titlexx span').text(title);
	console.log("#tab-"+current);
	$('.not-dqtab').find("#tab-"+current).addClass('current');
}

function getPlatform(){
	var platform=["Win32","Android","iOS"];
	for(var i=0;i<platform.length;i++){
		if(navigator.platform.indexOf(platform[i])>-1){
			if(platform[i] == "Win32");
			$('header').addClass('gialap');
		}
	}
}
getPlatform();





/* top search */

$('.search_text').click(function(){
	$(this).next().slideToggle(200);
	$('.list_search').show();
})

$('.list_search .search_item').on('click', function (e) {
	$('.list_search').hide();

	var optionSelected = $(this);

	/*
  var id = optionSelected.attr('data-coll-id');
  var handle = optionSelected.attr('data-coll-handle');
  var coll_name = optionSelected.attr('data-coll-name');
  */

	var title = optionSelected.text();
	//var filter = '(collectionid:product' + (id == 0 ? '>=0' : ('=' + id)) + ')';


	//console.log(coll_name);
	$('.search_text').text(title);
	var h = $(".collection-selector").width() + 10;

	$('.search-area form input').css('padding-left',h + 'px');

	/*
  $('.ultimate-search .collection_id').val(filter);
  $('.ultimate-search .collection_handle').val(handle);
  $('.ultimate-search .collection_name').val(coll_name);
  */

	$(".search-text").focus();
	optionSelected.addClass('active').siblings().removeClass('active');
	//console.log($('.kd_search_text').innerWidth());


	//$('.list_search').slideToggle(0);

	/*
  sessionStorage.setItem('last_search', JSON.stringify({
    title: title,
    handle: handle,
    filter: filter,
    name: coll_name
  }));
  */  
});


$('.header_search form button').click(function(e) {
	if($(window).width() > 992){
		e.preventDefault();
		searchCollection();
		setSearchStorage('.header_search form');
	}
});

$('#mb_search').click(function(){
	$('.mb_header_search').slideToggle('fast');
});

$('.fi-title.drop-down').click(function(){
	$(this).toggleClass('opentab');
});



function searchCollection() {
	var collectionId = $('.list_search .search_item.active').attr('data-coll-id');
	var searchVal = $('.header_search input[type="search"]').val();
	var url = '';
	if(collectionId == 0) {
		url = '/search?q='+ searchVal;
	}
	else {
		url = '/search?q=collections:'+ collectionId +' AND name:' + searchVal;
		/*
    if(searchVal != '') {
      url = '/search?type=product&q=' + searchVal + '&filter=(collectionid:product=' + collectionId + ')';
    }
    else {
      url = '/search?type=product&q=filter=(collectionid:product=' + collectionId + ')';
    }
    */
	}
	window.location=url;
}


/*** Search Storage ****/

function setSearchStorage(form_id) {
	var seach_input = $(form_id).find('.search-text').val();
	var search_collection = $(form_id).find('.list_search .search_item.active').attr('data-coll-id');
	sessionStorage.setItem('search_input', seach_input);
	sessionStorage.setItem('search_collection', search_collection);
}
function getSearchStorage(form_id) {
	var search_input_st = ''; // sessionStorage.getItem('search_input');
	var search_collection_st = ''; // sessionStorage.getItem('search_collection');
	if(sessionStorage.search_input != '') {
		search_input_st = sessionStorage.search_input;
	}
	if(sessionStorage.search_collection != '') {
		search_collection_st = sessionStorage.search_collection;
	}
	$(form_id).find('.search-text').val(search_input_st);
	$(form_id).find('.search_item[data-coll-id="'+search_collection_st+'"]').addClass('active').siblings().removeClass('active');
	var search_key = $(form_id).find('.search_item[data-coll-id="'+search_collection_st+'"]').text();
	if(search_key != ''){
		$(form_id).find('.collection-selector .search_text').text(search_key);
	}
	//$(form_id).find('.search_collection option[value="'+search_collection_st+'"]').prop('selected', true);
}
function resetSearchStorage() {
	sessionStorage.removeItem('search_input');
	sessionStorage.removeItem('search_collection');
}
$('li.lev-1.nav-item.has-mega.mega-menu').hover(function(e){
	if($(window).width() > 1200){
		var meh = $('.cate-sidebar').height();
		$('li.lev-1.nav-item.has-mega.mega-menu .mega-menu-content').css('min-height',meh +1);
	}
})
$(window).load(function() {
	if($(window).width() > 992){

		getSearchStorage('.header_search form');
		resetSearchStorage();
		var h = $(".collection-selector").width() + 10;
		$('.search-area form input').css('padding-left',h + 'px');
		$('.bot-header-left').mouseover(function(e){
			$('.catogory-other-page').addClass('active');
		})
	}

});

$('.header-nav li>.fa.fa-angle-right').click(function(e){

	e.preventDefault();
	$(this).parent().toggleClass('open');

})


$('.xemthem').click(function(e){
	e.preventDefault();
	$('ul.site-nav>li').css('display','block');
	$(this).hide();
	$('.thugon').show();
})
$('.thugon').click(function(e){
	e.preventDefault();
	$('ul.site-nav>li').css('display','none');
	$(this).hide();
	$('.xemthem').show();
})


$('.sidebar .side-menu nav .nav>li> .fa.f-right').click(function(e){
	
	e.preventDefault();
	$(this).parent().toggleClass('active');
})