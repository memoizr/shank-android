package memoizrlabs.com.shankandroid

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.view.View
import com.memoizrlabs.Scope
import com.memoizrlabs.ScopedCache
import com.memoizrlabs.Shank.with

val View.withParentActivityScope: ScopedCache get() = with(parentActivityScope)
val Fragment.withParentActivityScope: ScopedCache get() = with(getScope(activity))

val View.parentActivityScope: Scope get() = getScope(context)
val Fragment.parentActivityScope: Scope get() = getScope(context)

val Activity.withThisScope: ScopedCache get() = with(getScope(this))

private fun getScope(ctx: Context): Scope = when (ctx) {
    is Scoped -> ctx.scope
    is ContextThemeWrapper -> getScope(ctx.baseContext)
    is android.view.ContextThemeWrapper -> getScope(ctx.baseContext)
    is ContextWrapper -> getScope(ctx.baseContext)
    else -> throw IllegalArgumentException("Context $ctx is not Scopable.")
}
