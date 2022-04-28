package com.app.todolist.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FragmentReplace(val container: Int) : FragmentBehaviour() {
    override fun execute(transaction: FragmentTransaction, fragment: Fragment, tag: String?) {
        transaction.replace(container, fragment, tag)
    }

}