package com.spirit.cargo.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/** Can be used for swipe to delete functionality, use ItemTouchHelper.LEFT as direction
 * for example. */
fun RecyclerView.onSwipe(
    direction: Int,
    block: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit
) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, direction) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            block(viewHolder, direction)
        }
    }).apply { attachToRecyclerView(this@onSwipe) }
}
