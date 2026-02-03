package io.composelive.app.android

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.NoLiveLiterals
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

@NoLiveLiterals
abstract class BaseActivity : ComponentActivity() {
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    protected fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url.toUri()
        startActivity(intent)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
