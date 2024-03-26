package org.example.controller;

/**
 * お釣り処理のためのリザルトクラス
 */
public record ReturnChangeResult(int price, String message) {}
