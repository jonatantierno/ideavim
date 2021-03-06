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

package com.maddyhome.idea.vim.action.change.insert;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.action.VimCommandAction;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.MappingMode;
import com.maddyhome.idea.vim.command.SelectionType;
import com.maddyhome.idea.vim.handler.VisualOperatorActionHandler;
import com.maddyhome.idea.vim.group.visual.VimSelection;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author vlan
 */
public class VisualBlockAppendAction extends VimCommandAction {
  public VisualBlockAppendAction() {
    super(new VisualOperatorActionHandler.SingleExecution() {
      @Override
      public boolean executeForAllCarets(@NotNull Editor editor,
                                         @NotNull DataContext context,
                                         @NotNull Command cmd,
                                         @NotNull Map<Caret, ? extends VimSelection> caretsAndSelections) {
        if (editor.isOneLineMode()) return false;
        VimSelection range = caretsAndSelections.values().stream().findFirst().orElse(null);
        if (range == null) return false;
        if (range.getType() == SelectionType.BLOCK_WISE) {
          return VimPlugin.getChange().blockInsert(editor, context, range.toVimTextRange(false), true);
        }
        else {
          VimPlugin.getChange().insertAfterLineEnd(editor, context);
          return true;
        }
      }
    });
  }

  @NotNull
  @Override
  public Set<MappingMode> getMappingModes() {
    return MappingMode.V;
  }

  @NotNull
  @Override
  public Set<List<KeyStroke>> getKeyStrokesSet() {
    return parseKeysSet("A");
  }

  @NotNull
  @Override
  public Command.Type getType() {
    return Command.Type.INSERT;
  }

  @Override
  public EnumSet<CommandFlags> getFlags() {
    return EnumSet.of(CommandFlags.FLAG_MULTIKEY_UNDO, CommandFlags.FLAG_EXIT_VISUAL);
  }
}
