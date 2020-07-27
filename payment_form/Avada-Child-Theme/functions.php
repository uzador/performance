<?php

function theme_enqueue_styles() {
    wp_enqueue_style( 'child-style', get_stylesheet_directory_uri() . '/style.css', array( 'avada-stylesheet' ) );
}
add_action( 'wp_enqueue_scripts', 'theme_enqueue_styles' );

function avada_lang_setup() {
	$lang = get_stylesheet_directory() . '/languages';
	load_child_theme_textdomain( 'Avada', $lang );
}
add_action( 'after_setup_theme', 'avada_lang_setup' );

/**
* Load js and css for berluki customized payment form
*/
function load_custom_payment_form_scripts() {
    if ( is_page('8264') || is_page('33485') ) {
	wp_register_style( 'custom-payment-form-css1', get_theme_file_uri() . '/css/validationEngine.jquery.css', array(), '0.0.1', 'all' );
	wp_enqueue_style ('custom-payment-form-css1');

	wp_register_style( 'custom-payment-form-css2', get_theme_file_uri() . '/css/bootstrap.min.css', array(), '0.0.1', 'all' );
	wp_enqueue_style ('custom-payment-form-css2');

	wp_register_script( 'custom-payment-form-js1', get_theme_file_uri() . '/js/jquery-3.4.1.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js1');

  	wp_register_script( 'custom-payment-form-js2', get_theme_file_uri() . '/js/jquery.validationEngine-ru.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js2');

	wp_register_script( 'custom-payment-form-js3', get_theme_file_uri() . '/js/jquery.validationEngine.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js3');

	wp_register_script( 'custom-payment-form-js4', get_theme_file_uri() . '/js/payment_form_lib_bs.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js4');

	wp_register_script( 'custom-payment-form-js5', get_theme_file_uri() . '/js/bootstrap.bundle.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js5');
    }

}

add_action( 'wp_enqueue_scripts', 'load_custom_payment_form_scripts' );

//berluki insertions

include_once('inc/init.php');
