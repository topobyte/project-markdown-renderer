// Copyright 2017 Sebastian Kuerten
//
// This file is part of project-markdown-renderer.
//
// project-markdown-renderer is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// project-markdown-renderer is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with project-markdown-renderer. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.markdownprojects;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import de.topobyte.melon.paths.PathUtil;

public class ProjectMarkdownRenderer
{

	final static Logger logger = LoggerFactory
			.getLogger(ProjectMarkdownRenderer.class);

	public void renderDir(Path dirInput, Path dirOutput) throws IOException
	{
		List<Path> files = PathUtil.findRecursive(dirInput, "*.md");
		for (Path file : files) {
			String basename = PathUtil.getBasename(file);
			String htmlName = basename + ".html";

			Path relative = dirInput.relativize(file);
			Path relativeHtml = relative.resolveSibling(htmlName);
			Path output = dirOutput.resolve(relativeHtml);

			logger.debug("input: " + file);
			logger.debug("output: " + output);
			renderFile(file, output);
		}
	}

	private void renderFile(Path input, Path output) throws IOException
	{
		InputStream is = Files.newInputStream(input);
		String text = IOUtils.toString(is);
		is.close();

		MutableDataSet options = new MutableDataSet();

		Extension tables = new TablesExtension() {

			@Override
			public void rendererOptions(MutableDataHolder options)
			{
				options.set(TablesExtension.CLASS_NAME, "table");
			}

		};

		Extension strikethrough = StrikethroughExtension.create();

		options.set(Parser.EXTENSIONS, Arrays.asList(tables, strikethrough));

		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();

		Document document = parser.parse(text);
		String html = renderer.render(document);

		logger.debug(html);

		PathUtil.createParentDirectories(output);

		OutputStream os = Files.newOutputStream(output);
		IOUtils.write(html, os);
		os.close();
	}

}
