package viewmodel

import kotlinx.coroutines.flow.StateFlow
import model.Game

interface IViewGameModel {
    val games: StateFlow<List<Game>>
}
