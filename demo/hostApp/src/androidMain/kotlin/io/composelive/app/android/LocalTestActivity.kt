package io.composelive.app.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.NoLiveLiterals
import androidx.core.view.WindowCompat

@NoLiveLiterals
class LocalTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Treehouse()
        }
    }
}
