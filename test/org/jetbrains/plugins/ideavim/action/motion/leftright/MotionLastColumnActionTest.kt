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
import com.maddyhome.idea.vim.helper.VimBehaviourDiffers
import org.jetbrains.plugins.ideavim.VimTestCase

class MotionLastColumnActionTest : VimTestCase() {
    fun `test dollar motion`() {
        val keys = parseKeys("$")
        val before = """
            A Discovery

            I ${c}found it in a legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            I found it in a legendary lan${c}d
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    fun `test dollar motion with motion to longer line`() {
        val keys = parseKeys("\$j")
        val before = """
            A Discovery

            I ${c}found it in a legendary land
            all rocks and lavender and tufted grass,[ additional symbols]
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            I found it in a legendary land
            all rocks and lavender and tufted grass,[ additional symbols${c}]
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    fun `test dollar motion in visual block mode`() {
        val keys = parseKeys("<C-V>jj\$")
        val before = """
            A Discovery

            I ${c}found it in a legendary land
            all rocks and lavender and tufted grass,[ additional symbols]
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            I ${s}found it in a legendary lan${c}d${se}
            al${s}l rocks and lavender and tufted grass,[ additional symbols${c}]${se}
            wh${s}ere it was settled on some sodden sand${c}${se}
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_BLOCK)
    }

    @VimBehaviourDiffers(originalVimAfter = """
            A Discovery

            I ${s}found it in a legendary lan${c}d${se}
            al${s}l rocks and lavender and tufted grass${c},${se}[ additional symbols]
            wh${s}ere it was settled on some sodden san${c}d${se}
            hard by the torrent of a mountain pass.
    """)
    fun `test dollar motion in visual block mode with left motion`() {
        val keys = parseKeys("<C-V>jj\$h")
        val before = """
            A Discovery

            I ${c}found it in a legendary land
            all rocks and lavender and tufted grass,[ additional symbols]
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            I ${s}found it in a legendary lan${c}d${se}
            al${s}l rocks and lavender and tufted grass,[ additional symbols${c}]${se}
            wh${s}ere it was settled on some sodden san${c}d${se}
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_BLOCK)
    }
}