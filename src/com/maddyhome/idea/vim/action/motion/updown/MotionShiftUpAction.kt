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

package com.maddyhome.idea.vim.action.motion.updown

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.action.VimCommandAction
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.group.MotionGroup
import com.maddyhome.idea.vim.handler.ShiftedArrowKeyHandler
import com.maddyhome.idea.vim.helper.EditorHelper
import com.maddyhome.idea.vim.helper.isEndAllowed
import com.maddyhome.idea.vim.helper.mode
import com.maddyhome.idea.vim.helper.vimForEachCaret
import com.maddyhome.idea.vim.helper.vimLastColumn
import javax.swing.KeyStroke

/**
 * @author Alex Plate
 */

private object MotionShiftUpActionHandler : ShiftedArrowKeyHandler() {
  override fun motionWithKeyModel(editor: Editor, context: DataContext, cmd: Command) {
    editor.vimForEachCaret { caret ->
      val vertical = VimPlugin.getMotion().moveCaretVertical(editor, caret, -cmd.count)
      val col = caret.vimLastColumn
      MotionGroup.moveCaret(editor, caret, vertical)

      val pos = caret.visualPosition
      val lastColumn = EditorHelper.lastColumnForLine(editor, pos.line, editor.mode.isEndAllowed)
      val targetColumn = if (pos.column != lastColumn) pos.column else col
      caret.vimLastColumn = targetColumn
    }
  }

  override fun motionWithoutKeyModel(editor: Editor, context: DataContext, cmd: Command) {
    VimPlugin.getMotion().scrollFullPage(editor, -cmd.count)
  }
}

class MotionShiftUpAction : VimCommandAction(MotionShiftUpActionHandler) {
  override fun getMappingModes(): MutableSet<MappingMode> = MappingMode.NVS

  override fun getKeyStrokesSet(): MutableSet<MutableList<KeyStroke>> = parseKeysSet("<S-Up>")

  override fun getType(): Command.Type = Command.Type.OTHER_READONLY
}