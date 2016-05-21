package memoizrlabs.com.shankandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.memoizrlabs.Scope.scope
import java.util.*
import java.util.UUID.randomUUID

abstract class ShankAppCompatActivity : AppCompatActivity(), Scoped {
    private val STATE_SCOPE_ID = "STATE_SCOPE_ID"
    private lateinit var scopeUUID: UUID

    open protected val finalAction = {}

    override val scope by lazy { scope(scopeUUID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scopeUUID = savedInstanceState?.getSerializable(STATE_SCOPE_ID) as UUID? ?: randomUUID()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_SCOPE_ID, scopeUUID)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) { scope.clearWithFinalAction { finalAction() } }
    }
}

