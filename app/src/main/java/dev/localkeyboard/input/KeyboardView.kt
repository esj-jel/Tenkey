package dev.localkeyboard.input

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.view.MotionEvent
import android.view.View
import kotlin.math.max

class KeyboardView(context: Context, private val callbacks: Callbacks) : View(context) {
    interface Callbacks {
        fun onText(text: String)
        fun onDelete()
        fun onEnter()
        fun onGlobe()
        fun onJapaneseMode()
        fun onUtility(panel: UtilityPanel)
        fun onSpaceCursor(delta: Int)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var downX = 0f
    private var downY = 0f
    private var spaceDown = false
    private var state = KeyboardState()
    private val hiragana = listOf("あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ")
    private val katakana = listOf("ア", "カ", "サ", "タ", "ナ", "ハ", "マ", "ヤ", "ラ", "ワ")

    fun render(newState: KeyboardState) { state = newState; invalidate() }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat(); val h = height.toFloat()
        paint.shader = LinearGradient(0f, 0f, w, h, Color.rgb(248, 243, 255), Color.rgb(226, 242, 250), Shader.TileMode.CLAMP)
        canvas.drawRect(0f, 0f, w, h, paint); paint.shader = null
        val labels = when (state.utilityPanel) {
            UtilityPanel.EMOJI -> listOf("😀", "😂", "🥳", "❤️", "👍", "✨", "🎉", "😅", "😭", "🤔")
            UtilityPanel.KAOMOJI -> listOf("(＾▽＾)", "(•‿•)", "(╥﹏╥)", "¯\\_(ツ)_/¯", "(づ｡◕‿‿◕｡)づ")
            UtilityPanel.CLIPBOARD -> listOf("Clipboard", "Paste", "Pin", "History", "Clear")
            UtilityPanel.DEVTOOLS -> listOf("{ }", "Tab", "Esc", "←", "→", "↑", "↓")
            UtilityPanel.NONE -> if (state.languageTag == "ja") if (state.japaneseMode == JapaneseMode.HIRAGANA) hiragana else katakana else listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
        }
        val rows = if (labels.size <= 5) 1 else 2
        val keyW = max(1f, (w - 28f) / 5f); val keyH = if (rows == 1) h * .48f else h * .32f
        labels.forEachIndexed { index, label ->
            val col = index % 5; val row = index / 5
            drawKey(canvas, label, 8f + col * keyW, 8f + row * (keyH + 8f), keyW - 6f, keyH)
        }
        val y = h - h * .25f
        drawKey(canvas, "🌐", 8f, y, keyW - 6f, h * .19f)
        drawKey(canvas, if (state.japaneseMode == JapaneseMode.HIRAGANA) "カナ" else "かな", keyW + 8f, y, keyW - 6f, h * .19f)
        drawKey(canvas, "😀", keyW * 2 + 8f, y, keyW - 6f, h * .19f)
        drawKey(canvas, "space", keyW * 3 + 8f, y, keyW * 1.3f - 6f, h * .19f)
        drawKey(canvas, "⌫", keyW * 4.3f + 8f, y, keyW * .7f - 10f, h * .19f)
    }

    private fun drawKey(canvas: Canvas, label: String, x: Float, y: Float, w: Float, h: Float) {
        paint.color = Color.argb(210, 255, 255, 255); canvas.drawRoundRect(RectF(x, y, x + w, y + h), 16f, 16f, paint)
        paint.color = Color.rgb(50, 43, 62); paint.textSize = if (label.length > 5) 22f else 30f; paint.textAlign = Paint.Align.CENTER
        canvas.drawText(label, x + w / 2, y + h / 2 - (paint.ascent() + paint.descent()) / 2, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val keyW = max(1f, (width - 28f) / 5f); val y = height - height * .25f
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> { downX = event.x; downY = event.y; spaceDown = downY > y && downX > keyW * 3 && downX < keyW * 4.3f; return true }
            MotionEvent.ACTION_MOVE -> if (spaceDown) { callbacks.onSpaceCursor(((event.x - downX) / 18f).toInt()); downX = event.x; return true }
            MotionEvent.ACTION_UP -> {
                if (spaceDown) { spaceDown = false; return true }
                if (downY > y) when {
                    downX < keyW -> callbacks.onGlobe()
                    downX < keyW * 2 -> callbacks.onJapaneseMode()
                    downX < keyW * 3 -> callbacks.onUtility(UtilityPanel.EMOJI)
                    downX < keyW * 4.3f -> callbacks.onText(" ")
                    else -> callbacks.onDelete()
                } else {
                    val index = ((downY / (height * .34f)).toInt() * 5 + (downX / keyW).toInt())
                    val labels = if (state.japaneseMode == JapaneseMode.HIRAGANA) hiragana else katakana
                    if (index in labels.indices) callbacks.onText(labels[index]) else callbacks.onEnter()
                }
                return true
            }
        }
        return true
    }
}
