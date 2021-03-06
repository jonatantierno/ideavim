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

@file:Suppress("RemoveCurlyBracesFromTemplate")

package org.jetbrains.plugins.ideavim.action.motion.leftright

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import com.maddyhome.idea.vim.option.Options
import org.jetbrains.plugins.ideavim.VimListConfig
import org.jetbrains.plugins.ideavim.VimListOptionDefault
import org.jetbrains.plugins.ideavim.VimListOptionTestCase
import org.jetbrains.plugins.ideavim.VimListOptionTestConfiguration

class MotionArrowLeftActionTest : VimListOptionTestCase(Options.KEYMODEL) {
    @VimListOptionDefault
    fun `test visual default options`() {
        doTest(parseKeys("v", "<Left>"),
                """
                A Discovery

                I ${c}found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I${s}${c} f${se}ound it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopsel"]))
    fun `test visual stopsel`() {
        doTest(parseKeys("v", "<Left>"),
                """
                A Discovery

                I ${c}found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I${c} found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopselect"]))
    fun `test visual stopselect`() {
        doTest(parseKeys("v", "<Left>"),
                """
                A Discovery

                I ${c}found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I${s}${c} f${se}ound it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopvisual"]))
    fun `test visual stopvisual`() {
        doTest(parseKeys("v", "<Left>"),
                """
                A Discovery

                I ${c}found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I${c} found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopvisual"]))
    fun `test visual stopvisual multicaret`() {
        doTest(parseKeys("v", "<Left>"),
                """
                A Discovery

                I ${c}found it in a legendary land
                all rocks and ${c}lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I${c} found it in a legendary land
                all rocks and${c} lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }
}