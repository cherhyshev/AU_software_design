from utils.game_states import GameStates


def handle_keys(user_input, game_state):
    # Метод, который, в зависимости от состояния персонажа,
    # меняет назначение клавиш
    if user_input:
        if game_state == GameStates.PLAYERS_TURN:
            return handle_player_turn_keys(user_input)
        elif game_state == GameStates.PLAYER_DEAD:
            return handle_player_dead_keys(user_input)
        elif game_state == GameStates.TARGETING:
            return handle_targeting_keys(user_input)
        elif game_state in (GameStates.SHOW_INVENTORY, GameStates.DROP_INVENTORY):
            return handle_inventory_keys(user_input)
        elif game_state == GameStates.LEVEL_UP:
            return handle_level_up_menu(user_input)
        elif game_state == GameStates.CHARACTER_SCREEN:
            return handle_character_screen(user_input)

    return {}

def handle_targeting_keys(user_input):
    # Метод, отменяющий наведение мышью
    # при наложении заклинания
    if user_input.key == 'ESCAPE':
        return {'exit': True}

    return {}

def handle_mouse(mouse_event):
    # Метод, принимающий клики мышью
    # при наложении заклинания
    if mouse_event:
        (x, y) = mouse_event.cell

        if mouse_event.button == 'LEFT':
            return {'left_click': (x, y)}
        elif mouse_event.button == 'RIGHT':
            return {'right_click': (x, y)}

    return {}

def handle_player_turn_keys(user_input):
    # Метод, переводящий нажатие клавиш
    # в движение персонажа
    key_char = user_input.char

    # Movement keys
    if user_input.key == 'UP' or user_input.key == 'KP8':
        return {'move': (0, -1)}
    elif user_input.key == 'DOWN' or user_input.key == 'KP2':
        return {'move': (0, 1)}
    elif user_input.key == 'LEFT' or user_input.key == 'KP4':
        return {'move': (-1, 0)}
    elif user_input.key == 'RIGHT' or user_input.key == 'KP6':
        return {'move': (1, 0)}
    elif user_input.key == 'KP7':
        return {'move': (-1, -1)}
    elif user_input.key == 'KP9':
        return {'move': (1, -1)}
    elif user_input.key == 'KP1':
        return {'move': (-1, 1)}
    elif user_input.key == 'KP3':
        return {'move': (1, 1)}

    if key_char == 's' or user_input.key == 'KP5':
        return {'pickup': True}

    elif key_char == 'i':
        return {'show_inventory': True}

    elif key_char == 'd':
        return {'drop_inventory': True}

    elif key_char == '.' and user_input.shift:
        return {'take_stairs': True}

    elif key_char == 'c':
        return {'show_character_screen': True}

    elif key_char == 'z':
        return {'wait': True}

    if user_input.key == 'ENTER' and user_input.alt:
        # Alt+Enter: toggle full screen
        return {'fullscreen': True}
    elif user_input.key == 'ESCAPE':
        # Exit the game
        return {'exit': True}

    # No key was pressed
    return {}

def handle_player_dead_keys(user_input):
    # Управление мертвым персонажем
    key_char = user_input.char

    if key_char == 'i':
        return {'show_inventory': True}

    if user_input.key == 'ENTER' and user_input.alt:
        # Alt+Enter: toggle full screen
        return {'fullscreen': True}
    elif user_input.key == 'ESCAPE':
        # Exit the game
        return {'exit': True}

    # No key was pressed
    return {}

def handle_inventory_keys(user_input):
    # Управление персонажем в окне инвентаря
    if not user_input.char:
        return {}

    index = ord(user_input.char) - ord('a')

    if index >= 0:
        return {'inventory_index': index}

    if user_input.key == 'ENTER' and user_input.alt:
        # Alt+Enter: toggle full screen
        return {'fullscreen': True}
    elif user_input.key == 'ESCAPE':
        # Exit the game
        return {'exit': True}

    return {}

def handle_main_menu(user_input):
    # Управление в основном меню
    if user_input:
        key_char = user_input.char

        if key_char == 'a':
            return {'new_game': True}
        elif key_char == 'b':
            return {'load_game': True}
        elif key_char == 'c' or user_input.key == 'ESCAPE':
            return {'exit': True}

    return {}

def handle_level_up_menu(user_input):
    # Управление в меню повышения уровня
    if user_input:
        key_char = user_input.char

        if key_char == 'a':
            return {'level_up': 'hp'}
        elif key_char == 'b':
            return {'level_up': 'str'}
        elif key_char == 'c':
            return {'level_up': 'def'}

    return {}

def handle_character_screen(user_input):
    # Управление в меню характеристик
    if user_input.key == 'ESCAPE':
        return {'exit': True}

    return {}
