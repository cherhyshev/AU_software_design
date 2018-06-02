#! /usr/bin/env python
# -*- coding: utf-8 -*-

from enum import Enum, auto


class EquipmentSlots(Enum):
    """
    Категории надеваемых объектов. Сейчас только руки,
    но можно расширить шлемами и пр.
    """
    MAIN_HAND = auto()
    OFF_HAND = auto()
