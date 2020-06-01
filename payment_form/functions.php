<?php
/**
 * Extra files & functions are hooked here.
 *
 * Displays all of the head element and everything up until the "site-content" div.
 *
 * @package Avada
 * @subpackage Core
 * @since 1.0
 */

// Do not allow directly accessing this file.
if ( ! defined( 'ABSPATH' ) ) {
	exit( 'Direct script access denied.' );
}

if ( ! defined( 'AVADA_VERSION' ) ) {
	define( 'AVADA_VERSION', '6.2.3' );
}

if ( ! defined( 'AVADA_MIN_PHP_VER_REQUIRED' ) ) {
	define( 'AVADA_MIN_PHP_VER_REQUIRED', '5.6' );
}

if ( ! defined( 'AVADA_MIN_WP_VER_REQUIRED' ) ) {
	define( 'AVADA_MIN_WP_VER_REQUIRED', '4.7' );
}

// Developer mode.
if ( ! defined( 'AVADA_DEV_MODE' ) ) {
	define( 'AVADA_DEV_MODE', false );
}

/**
 * Compatibility check.
 *
 * Check that the site meets the minimum requirements for the theme before proceeding.
 *
 * @since 6.0
 */
if ( version_compare( $GLOBALS['wp_version'], AVADA_MIN_WP_VER_REQUIRED, '<' ) || version_compare( PHP_VERSION, AVADA_MIN_PHP_VER_REQUIRED, '<' ) ) {
	require_once get_template_directory() . '/includes/bootstrap-compat.php';
	return;
}

/**
 * Bootstrap the theme.
 *
 * @since 6.0
 */
require_once get_template_directory() . '/includes/bootstrap.php';

/**
* Load js and css for customized payment form
*/
function load_custom_payment_form_scripts() {
    if ( is_page('8264') || is_page('33485') ) {
	wp_register_style( 'custom-payment-form-css1', get_template_directory_uri() . '/css/validationEngine.jquery.css', array(), '0.0.1', 'all' );
	wp_enqueue_style ('custom-payment-form-css1');

	wp_register_style( 'custom-payment-form-css2', get_template_directory_uri() . '/css/bootstrap.min.css', array(), '0.0.1', 'all' );
	wp_enqueue_style ('custom-payment-form-css2');

	wp_register_script( 'custom-payment-form-js1', get_template_directory_uri() . '/js/jquery-3.4.1.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js1');

  	wp_register_script( 'custom-payment-form-js2', get_template_directory_uri() . '/js/jquery.validationEngine-ru.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js2');

	wp_register_script( 'custom-payment-form-js3', get_template_directory_uri() . '/js/jquery.validationEngine.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js3');

	wp_register_script( 'custom-payment-form-js4', get_template_directory_uri() . '/js/payment_form_lib_bs.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js4');

	wp_register_script( 'custom-payment-form-js5', get_template_directory_uri() . '/js/bootstrap.bundle.min.js', array(), '0.0.1', true );
	wp_enqueue_script('custom-payment-form-js5');
    }

}

add_action( 'wp_enqueue_scripts', 'load_custom_payment_form_scripts' );

/* Omit closing PHP tag to avoid "Headers already sent" issues. */

