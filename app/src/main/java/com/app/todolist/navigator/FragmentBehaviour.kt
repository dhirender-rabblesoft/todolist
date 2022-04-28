package com.app.todolist.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


abstract class FragmentBehaviour {
    abstract fun execute(transaction: FragmentTransaction, fragment: Fragment, tag: String?)
}