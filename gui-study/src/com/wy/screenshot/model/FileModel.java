package com.wy.screenshot.model;

import java.awt.image.BufferedImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileModel {

	private String filename;

	private BufferedImage buffer;
}