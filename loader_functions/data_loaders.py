#! /usr/bin/env python
# -*- coding: utf-8 -*-

import os

import shelve


def save_game(player, entities, game_map, message_log, game_state):
    """
    Метод, обеспечивающий сохранение игрового состояния в файл
    :param player:
    :param entities:
    :param game_map:
    :param message_log:
    :param game_state:
    :return:
    """
    with shelve.open('savegame.dat', 'n') as data_file:
        data_file['player_index'] = entities.index(player)
        data_file['entities'] = entities
        data_file['game_map'] = game_map
        data_file['message_log'] = message_log
        data_file['game_state'] = game_state


def load_game():
    """
    Метод, обеспечивающий загрузку игрового состояния из файла
    :return:
    """
    if not os.path.isfile('savegame.dat'):
        raise FileNotFoundError

    with shelve.open('savegame.dat', 'r') as data_file:
        player_index = data_file['player_index']
        entities = data_file['entities']
        game_map = data_file['game_map']
        message_log = data_file['message_log']
        game_state = data_file['game_state']

    player = entities[player_index]

    return player, entities, game_map, message_log, game_state
