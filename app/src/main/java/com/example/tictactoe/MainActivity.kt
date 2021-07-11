package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var PLAYER = true
    private var TURN_COUNT = 0
    private var boardStatus = Array(3) {
        IntArray(3)
    }
    private lateinit var board: Array<Array<Button>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = arrayOf(
                arrayOf(button1, button2, button3),
                arrayOf(button4, button5, button6),
                arrayOf(button7, button8, button9)
        )
        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }
        initializeBoardStatus()
        resetBtn.setOnClickListener {
            initializeBoardStatus()
            TURN_COUNT = 0
            PLAYER = true
            displayTv.text = "Player X Turn"
        }
    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button1 -> {
                updateValue(row = 0, col = 0, player = PLAYER)
            }
            R.id.button2 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
            }
            R.id.button3 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
            }
            R.id.button4 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
            }
            R.id.button5 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
            }
            R.id.button6 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
            }
            R.id.button7 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
            }
            R.id.button8 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
            }
            R.id.button9 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
            }
        }
        if (PLAYER) {
            updateDisplay("Player X Turn")
        } else {
            updateDisplay("Player O Turn")
        }
        if (TURN_COUNT == 9) {
            updateDisplay("Game Draw")
        }
        checkWinner()
    }

    private fun checkWinner() {
        //horizontal
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay("Player X Winner")
                    return
                } else if (boardStatus[i][0] == 0) {
                    updateDisplay("Player O Winner")
                    return
                }
            }
        }
        //Vertical
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay("Player X Winner")
                    return
                } else if (boardStatus[0][i] == 0) {
                    updateDisplay("Player O Winner")
                    return
                }
            }
        }
        //Diagonal1
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay("Player X Winner")
                return
            } else if (boardStatus[0][0] == 0) {
                updateDisplay("Player O Winner")
                return
            }
        }
        //Diagonal2
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                updateDisplay("Player X Winner")
                return
            } else if (boardStatus[0][2] == 0) {
                updateDisplay("Player O Winner")
                return
            }
        }

    }

    private fun updateDisplay(text: String) {
        displayTv.text = text
        if (text.contains("Winner")) {
            disableAllButtons()
        }
    }

    private fun disableAllButtons() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text: String = if (player) "X" else "O"
        val value: Int = if (player) 1 else 0
        val color = if (player) "#FF0000" else "#114D05"
        board[row][col].apply {
            isEnabled = false
            this.text = text
            setTextColor(Color.parseColor(color))
        }
        boardStatus[row][col] = value
        PLAYER = !PLAYER
        TURN_COUNT++
    }
}
