#! /usr/bin/env python
# -*- coding: utf-8 -*-

from enum import Enum, auto


class GameStates(Enum):
    """
    Перечень состояний, в которых
    может находиться персонаж
    """
    PLAYERS_TURN = auto()
    ENEMY_TURN = auto()
    PLAYER_DEAD = auto()
    SHOW_INVENTORY = auto()
    DROP_INVENTORY = auto()
    TARGETING = auto()
    LEVEL_UP = auto()
    CHARACTER_SCREEN = auto()
