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

package org.jetbrains.plugins.ideavim.action.motion.updown

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import com.maddyhome.idea.vim.option.Options
import org.jetbrains.plugins.ideavim.VimListConfig
import org.jetbrains.plugins.ideavim.VimListOptionDefault
import org.jetbrains.plugins.ideavim.VimListOptionTestCase
import org.jetbrains.plugins.ideavim.VimListOptionTestConfiguration

class MotionArrowDownActionTest : VimListOptionTestCase(Options.KEYMODEL) {
    @VimListOptionDefault
    fun `test visual default options`() {
        doTest(parseKeys("v", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all ${s}rocks and lavender and tufted grass,
                wher${c}e${se} it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig( Options.KEYMODEL, ["stopsel"]))
    fun `test visual stopsel`() {
        doTest(parseKeys("v", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all rocks and lavender and tufted grass,
                wher${c}e it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopselect"]))
    fun `test visual stopselect`() {
        doTest(parseKeys("v", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all ${s}rocks and lavender and tufted grass,
                wher${c}e${se} it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopvisual"]))
    fun `test visual stopvisual`() {
        doTest(parseKeys("v", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all rocks and lavender and tufted grass,
                wher${c}e it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopvisual"]))
    fun `test visual stopvisual multicaret`() {
        doTest(parseKeys("v", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was ${c}settled on some sodden sand
                hard by the torrent of a mountain pass.
                """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all rocks and lavender and tufted grass,
                wher${c}e it was settled on some sodden sand
                hard by the t${c}orrent of a mountain pass.
                """.trimIndent(),
                CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, []))
    fun `test char select stopsel`() {
        doTest(parseKeys("gh", "<Down>"),
                """
                A Discovery

                I found it in a legendary land
                all ${c}rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                    """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                all ${s}rocks and lavender and tufted grass,
                where${c}${se} it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                    """.trimIndent(),
                CommandState.Mode.SELECT,
                CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopselect"]))
    fun `test char select simple move`() {
        doTest(parseKeys("gH", "<Down>"),
                """
                A Discovery

                ${c}I found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                    """.trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                ${c}all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by the torrent of a mountain pass.
                    """.trimIndent(),
                CommandState.Mode.COMMAND,
                CommandState.SubMode.NONE)
    }

    @VimListOptionTestConfiguration(VimListConfig(Options.KEYMODEL, ["stopselect"]))
    fun `test select multiple carets`() {
        doTest(parseKeys("gH", "<Down>"),
                """
                A Discovery

                ${c}I found it in a legendary land
                all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by ${c}the torrent of a mountain pass.""".trimIndent(),
                """
                A Discovery

                I found it in a legendary land
                ${c}all rocks and lavender and tufted grass,
                where it was settled on some sodden sand
                hard by ${c}the torrent of a mountain pass.""".trimIndent(),
                CommandState.Mode.COMMAND,
                CommandState.SubMode.NONE)
    }
}