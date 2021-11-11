# Tehdään alussa importit

import logger from logger
import summa import summa
import erotus import erotus

logger('aloitetaan ohjelma')

x = int(input('luku 1: '))
y = int(input('luku2 :'))
print(f'{x} + {y} = {summa(x, y)}')
print(f'{x} - {y} = {erotus(x, y)}')


logger('lopetetaa')
print('goodbye!')
