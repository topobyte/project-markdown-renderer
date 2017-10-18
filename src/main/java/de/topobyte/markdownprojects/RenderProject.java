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
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenderProject
{

	public static void main(String[] args) throws IOException
	{
		if (args.length != 2) {
			System.out
					.println("Usage: render-project <dir input> <dir output>");
			System.exit(1);
		}
		Path input = Paths.get(args[0]);
		Path output = Paths.get(args[1]);
		ProjectMarkdownRenderer renderer = new ProjectMarkdownRenderer();
		renderer.renderDir(input, output);
	}

}
