/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.motion.visual

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.action.VimCommandAction
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.handler.EditorActionHandlerBase
import com.maddyhome.idea.vim.helper.vimForEachCaret
import java.util.*
import javax.swing.KeyStroke

/**
 * @author vlan
 */
private object VisualSwapEndsActionHandler : EditorActionHandlerBase() {
  override fun execute(editor: Editor, context: DataContext, cmd: Command): Boolean {
    var ret = true
    editor.vimForEachCaret { ret = ret and VimPlugin.getVisualMotion().swapVisualEnds(editor, it) }
    return ret
  }
}

class VisualSwapEndsAction : VimCommandAction(VisualSwapEndsActionHandler) {

  override fun getMappingModes(): EnumSet<MappingMode> = MappingMode.V

  override fun getKeyStrokesSet(): Set<List<KeyStroke>> = parseKeysSet("o")

  override fun getType(): Command.Type = Command.Type.OTHER_READONLY
}

