package com.wy;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuComponent;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;

/**
 * 继承关系图
 * 
 * {@link Component}
 * 
 * ->{@link Container}:容器,一个特殊的组件,该组件中可以通过add()添加其他组件进来
 * -->{@link Window}:window组件 --->{@link Frame}:主要用到的组件 --->{@link Dialog}:对话框组件
 * ---->{@link FileDialog}:文件对话框组件 -->{@link Panel}:面板组件,不能单独存在
 * 
 * ->{@link Button} ->{@link Label} ->{@link Checkbox} ->{@link TextComponent}
 * -->{@link TextArea} -->{@link TextField}
 * 
 * {@link MenuComponent}:菜单组件
 * 
 * ->{@link MenuBar}
 * 
 * ->{@link MenuItem}-->{@link Menu}
 * 
 * 布局:
 * 
 * <pre>
 * {@link FlowLayout}:流式布局管理器,从左到右的顺序排序,Panel默认的布局管理器
 * {@link BorderLayout}:边界布局管理器,东南西北中,Frame默认的布局管理器
 * {@link GridLayout}:网格布局管理器,规则的矩阵
 * {@link CardLayout}:卡片布局管理器,选项卡
 * {@link GridBagLayout}:网格包布局管理器,非规则的矩阵
 * </pre>
 * 
 * @auther 飞花梦影
 * @date 2021-06-14 11:21:23
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class GuiApplication {

}