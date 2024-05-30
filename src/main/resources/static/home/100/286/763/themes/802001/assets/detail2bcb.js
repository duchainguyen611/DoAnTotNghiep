var selectCallback = function(variant, selector) {
	if (variant) {
		var form = jQuery('#' + selector.domIdPrefix).closest('form');

		for (var i=0,length=variant.options.length; i<length; i++) {

			var radioButton = form.find('.swatch[data-option-index="' + i + '"] :radio[value="' + variant.options[i] +'"]');

			if (radioButton.size()) {
				radioButton.get(0).checked = true;
			}
		}
	}
	var addToCart = jQuery('.form-product .btn-cart'),
		masp = jQuery('.masp'),
		form = jQuery('.form-product .form-groupx'),
		productPrice = jQuery('.details-pro .special-price .product-price'),
		qty = jQuery('.details-pro .inventory_quantity'),
		wishlist = jQuery('.details-pro .iwishAddWrapper'),
		comparePrice = jQuery('.details-pro .old-price .product-price-old');

	if(variant && variant.sku && variant.sku != null)
	{
		masp.text(variant.sku);
	}else{
		masp.text('Đang cập nhật');
	}

	
	if (variant && variant.available) {

		qty.html('<span>Còn hàng</span>');

		addToCart.text('Thêm vào giỏ hàng').removeAttr('disabled');
		if(variant.price == 0){
			productPrice.html('Liên hệ');
			comparePrice.hide();
			form.addClass('hidden');
			wishlist.addClass('btn-full');
		}else{
			wishlist.removeClass('btn-full');
			form.removeClass('hidden');
			productPrice.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));
												 // Also update and show the product's compare price if necessary
												 if ( variant.compare_at_price > variant.price ) {
							  comparePrice.html(Bizweb.formatMoney(variant.compare_at_price, "{{amount_no_decimals_with_comma_separator}}₫")).show();
							  } else {
							  comparePrice.hide();
		}
	}

} else {
	qty.html('<span>Hết hàng</span>');
	addToCart.text('Hết hàng').attr('disabled', 'disabled');
	if(variant){
		if(variant.price != 0){
			form.removeClass('hidden');
			wishlist.removeClass('btn-full');
			productPrice.html(Bizweb.formatMoney(variant.price, "{{amount_no_decimals_with_comma_separator}}₫"));
												 // Also update and show the product's compare price if necessary
												 if ( variant.compare_at_price > variant.price ) {
							  comparePrice.html(Bizweb.formatMoney(variant.compare_at_price, "{{amount_no_decimals_with_comma_separator}}₫")).show();
							  } else {
							  comparePrice.hide();
		}
	}else{
		productPrice.html('Liên hệ');
		comparePrice.hide();
		form.addClass('hidden');
		wishlist.addClass('btn-full');
	}
}else{
	productPrice.html('Liên hệ');
	comparePrice.hide();
	form.addClass('hidden');
	wishlist.addClass('btn-full');
}

}

/*begin variant image*/
if (variant && variant.image) {
	var originalImage = jQuery(".large-image img");
	var newImage = variant.image;
	var element = originalImage[0];
	Bizweb.Image.switchImage(newImage, element, function (newImageSizedSrc, newImage, element) {
		jQuery(element).parents('a').attr('href', newImageSizedSrc);
		jQuery(element).attr('src', newImageSizedSrc);
	});

	$('.checkurl').attr('href',$(this).attr('src'));
	$('.zoomContainer').remove();
	setTimeout(function(){
		if($(window).width() > 1200){
			$('.zoomContainer').remove();
			$('#zoom_01').elevateZoom({
				gallery:'gallery_01',
				zoomWindowWidth:420,
				zoomWindowHeight:500,
				zoomWindowOffetx: 10,
				easing : true,
				scrollZoom : false,
				cursor: 'pointer',
				galleryActiveClass: 'active',
				imageCrossfade: true

			});
		}

	},300);
}

/*end of variant image*/
};
jQuery(function($) {
	if(variantsize == true){

		new Bizweb.OptionSelectors('product-selectors', {
			product: productJson,
			onVariantSelected: selectCallback,
			enableHistoryState: true
		});
	}


	// Add label if only one product option and it isn't 'Title'. Could be 'Size'.
	if(productOptionsSize == 1){
		if(optionsFirst == 'Ngày'){
			$('.selector-wrapper:eq(0)').prepend('<label><i class="fa fa-calendar"></i> '+ optionsFirst +'</label>');
		}else{
			$('.selector-wrapper:eq(0)').prepend('<label>'+ optionsFirst +'</label>');
		}
	}

	// Hide selectors if we only have 1 variant and its title contains 'Default'.
	if(cdefault == 1){
		$('.selector-wrapper').hide();
	}
	$('.selector-wrapper').css({
		'text-align':'left',
		'margin-bottom':'15px'
	});
});

jQuery('.swatch :radio').change(function() {
	var optionIndex = jQuery(this).closest('.swatch').attr('data-option-index');
	var optionValue = jQuery(this).val();
	jQuery(this)
		.closest('form')
		.find('.single-option-selector')
		.eq(optionIndex)
		.val(optionValue)
		.trigger('change');
});

$(document).ready(function() {
	if($(window).width()>1200){
		setTimeout(function(){
			$('#zoom_01').elevateZoom({
				gallery:'gallery_01',
				zoomWindowWidth:420,
				zoomWindowHeight:500,
				zoomWindowOffetx: 10,
				easing : true,
				scrollZoom : false,
				cursor: 'pointer',
				galleryActiveClass: 'active',
				imageCrossfade: true

			});
		},500);
	}



});

var alias = '';
$(window).on("load resize",function(e){

	if($(window).width() > 1199){
		var he = $('.large-image').height();
		if (he > 500) {
			he = he - 200;
			$('#gallery_01').css('margin-top','100px');
			$('.product-image-block').removeClass('margin');

		}else{
			$('.product-image-block').addClass('margin');
			$('#gallery_01').css('margin-top','0');
		}
		$('#gallery_01').height(he - 30);
		if(he < 250){
			var items = 2;
		}else{
			if(he < 400){
				var items = 3;
			}else{
				if(he > 750){
					var items = 9;
				}else{

					if(he > 600){
						var items = 7;
					}else{
						var items = 5;
					}

				}
			}
		}
		$("#gallery_01.swiper-container").each( function(){
			var config = {
				spaceBetween: 15,
				slidesPerView: items,
				direction: $(this).data('direction'),
				paginationClickable: true,
				nextButton: '.swiper-button-next.fixlg',
				prevButton: '.swiper-button-prev.fixlg',
				grabCursor: true ,
				calculateHeight:true,
				height:he
			};
			var swiper = new Swiper(this, config);

		});

		$('.more-views .swiper-slide img').each(function(e){
			var t1 = (this.naturalHeight/this.naturalWidth);

			if(t1<1){
				$(this).parents('.swiper-slide').addClass('bethua');
			}
		})
	}else{
		$("#gallery_01.swiper-container").each( function(){
			var config = {
				spaceBetween: 15,
				slidesPerView: 4,
				direction: 'horizontal',
				paginationClickable: true,
				nextButton: '.swiper-button-next',
				prevButton: '.swiper-button-prev',
				grabCursor: true ,
				calculateHeight:true,

			};
			var swiper = new Swiper(this, config);

		});
	}
	$('.thumb-link').click(function(e){
		e.preventDefault();
		var hr = $(this).attr('href');
		$('#zoom_01').attr('src',hr);
	})
});
$('#gallery_01 img, .swatch-element label').click(function(e){
	$('.checkurl').attr('href',$(this).attr('src'));
	setTimeout(function(){
		if($(window).width() > 1200){
			$('.zoomContainer').remove();
			$('#zoom_01').elevateZoom({
				gallery:'gallery_01',
				zoomWindowWidth:420,
				zoomWindowHeight:500,
				zoomWindowOffetx: 10,
				easing : true,
				scrollZoom : false,
				cursor: 'pointer',
				galleryActiveClass: 'active',
				imageCrossfade: true

			});
		}

		if($(window).width() > 1199){
			var he = $('.large-image').height();
			if (he > 500) {
				he = he - 200;
				$('#gallery_01').css('margin-top','100px');
				$('.product-image-block').removeClass('margin');

			}else{
				$('.product-image-block').addClass('margin');
				$('#gallery_01').css('margin-top','0');
			}
			$('#gallery_01').height(he -30);

			if(he < 250){
				var items = 2;
			}else{
				if(he < 400){
					var items = 3;
				}else{
					if(he > 750){
						var items = 9;
					}else{
						if(he > 600){
							var items = 7;
						}else{
							var items = 5;
						}
					}
				}
			}
			$("#gallery_01.swiper-container").each( function(){
				var config = {
					spaceBetween: 15,
					slidesPerView: items,
					direction: $(this).data('direction'),
					paginationClickable: true,
					nextButton: '.swiper-button-next',
					prevButton: '.swiper-button-prev',
					grabCursor: true ,
					calculateHeight:true,
					height:he
				};
				var swiper = new Swiper(this, config);

			});
		}

	},400);

	setTimeout(function(){
		if($(window).width() > 1200){
			$('#zoom_01').elevateZoom({
				gallery:'gallery_01',
				zoomWindowWidth:420,
				zoomWindowHeight:500,
				zoomWindowOffetx: 10,
				easing : true,
				scrollZoom : false,
				cursor: 'pointer',
				galleryActiveClass: 'active',
				imageCrossfade: true

			});
		}
		if($(window).width() > 1199){
			var he = $('.large-image').height();
			if (he > 500) {
				he = he - 200;
				$('#gallery_01').css('margin-top','100px');
				$('.product-image-block').removeClass('margin');

			}else{
				$('.product-image-block').addClass('margin');
				$('#gallery_01').css('margin-top','0');
			}
			$('#gallery_01').height(he -30);

			if(he < 250){
				var items = 2;
			}else{
				if(he < 400){
					var items = 3;
				}else{
					if(he > 750){
						var items = 9;
					}else{
						if(he > 600){
							var items = 7;
						}else{
							var items = 5;
						}
					}
				}
			}
			$("#gallery_01.swiper-container").each( function(){
				var config = {
					spaceBetween: 15,
					slidesPerView: items,
					direction: $(this).data('direction'),
					paginationClickable: true,
					nextButton: '.swiper-button-next',
					prevButton: '.swiper-button-prev',
					grabCursor: true ,
					calculateHeight:true,
					height:he
				};
				var swiper = new Swiper(this, config);

			});
		}

	},1000);

})

$('#gallery_01 img').click(function(e){
	e.preventDefault();
	var fixsrc = $(this).parent().attr('data-image');
	//console.log(fixsrc);
	jQuery('.large-image img').parents('a').attr('href', fixsrc);
	jQuery('.large-image img').attr('src', fixsrc);
})
$('.more-views .swiper-slide .thumb-link').click(function(e){
	e.preventDefault();
	$('.more-views .swiper-slide').removeClass('active');
	$(this).parent().addClass('active');
})
function scrollToxx() {
	$('html, body').animate({ scrollTop: $('.product-tab.e-tabs').offset().top }, 'slow');
	$('.product-tab .tab-link').removeClass('current');
	$('.product-tab .tab-link[data-tab=tab-3]').addClass('current');
	$('.product-tab .tab-content').removeClass('current');
	$('.product-tab .tab-content#tab-3').addClass('current');

	return false;
}

setTimeout(function(){
	$('.sapo-product-reviews-badge').click(function(e) {
		$('html, body').animate({ scrollTop: $('.product-tab.e-tabs').offset().top }, 'slow');
		$('.tab-content, .product-tab .tab-link').removeClass('current');
		$('#tab-3, .product-tab .tab-link:nth-child(3)').addClass('current');
		return false;
	})
}, 500)