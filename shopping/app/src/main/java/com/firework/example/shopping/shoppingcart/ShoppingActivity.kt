package com.firework.example.shopping.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firework.example.shopping.R

class ShoppingActivity : AppCompatActivity(), ShoppingCartListener {
    @Suppress("UNCHECKED_CAST", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
        if (savedInstanceState == null) {
            loadFragment(ShoppingCartFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, null)
            .commit()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ShoppingActivity::class.java))
        }
    }

    override fun onCheckout() {
        finish()
    }
}
