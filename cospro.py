#1 순열조합
from itertools import permutations, combinations
totalList = [1,2,3,4,5,6]
permut = list(permutations(totalList, 3))
comb = list(combinations(totalList, 4))
print ("순열 리스트: ", permut)
print ("조합 리스트: ", permut)

#2 진수변환
dec = '101101100'
#n진수 string -> 10진수(integer)
print ("n진수 -> 10진수: ", int(dec), int(dec,2), int(dec,5))

decInteger = 38
#10진수 -> 2,8,16진수(나머진 안됨)
print ("10진수 -> 2,8,16진수 스트링: ", bin(decInteger), oct(decInteger), hex(decInteger))
#시험에서 앞에 0x빼기
print ("숫자만남기기: ", bin(decInteger)[2:], oct(decInteger)[2:], hex(decInteger)[2:])


#3 소수점 자리수(써먹은적없음)
import math
n = 13.45678
#2자리에서 반올림, 정수로 올림/내림/버림
print ("반올림 올림 등: ", round(n,2), math.ceil(n), math.floor(n), math.trunc(n))


#4 리스트
alist = [1,2,3,3,3,4,4]
print ("리스트 중복제거: ", list(set(alist)))

blist = alist.copy()
clist = alist
alist.append(11111)
print ("리스트 복사 비교: ", blist, clist)

m=3
n=4
mnlist = [[0 for i in range(m)]for i in range(n)]
print ("m*n 리스트: ", mnlist)

#--------------그냥 외운거-------------------------------
#달팽이 2차원배열
def snail():
    snail = [[-1 for i in range(3)] for i in range (4)]

    dcol = [1,0,-1,0]
    drow = [0,1,0,-1]
    dist = 0
    col = 0
    row = 0
    num = 0
    for i in range (4*3):
        #print (col, row, dist)

        snail[row][col] = num
        col = col+dcol[dist]
        row = row+drow[dist]
        if col>=3 or col<0 or row>=4 or row<0 or snail[row][col] != -1:
            col = col-dcol[dist]
            row = row-drow[dist]
            dist = (dist+1)%4
            col = col+dcol[dist]
            row = row+drow[dist]
        num+=1
    print (snail)

snail()
#탐색: 한줄바꾸기 종종 나와서 반복문/재귀 이해
graph = {
    'A': ['B'],
    'B': ['A', 'C', 'H'],
    'C': ['B', 'D'],
    'D': ['C', 'E', 'G'],
    'E': ['D', 'F'],
    'F': ['E'],
    'G': ['C'],
    'H': ['B', 'I', 'J', 'M'],
    'I': ['H'],
    'J': ['H', 'K'],
    'K': ['J', 'L'],
    'L': ['K'],
    'M': ['H']
}

#반복문으로 bfs, dfs
def bfs_iter(graph, start_node):
    visit = []
    queue = []

    queue.append(start_node)

    while queue:
        node = queue.pop(0)
        if node not in visit:
            visit.append(node)
            queue.extend(graph[node])
    return visit
print (bfs_iter(graph, 'A'))

def dfs_iter(graph, start_node):
    visit = []
    stack = []

    stack.append(start_node)

    while stack:
        node = stack.pop()
        if node not in visit:
            visit.append(node)
            stack.extend(reversed(graph[node]))
    return visit

print (dfs_iter(graph, 'A'))

#재귀함수로 dfs
def dfs_recur(graph, start, visited = []):
    visited.append(start)

    for node in graph:
        if node not in visited:
            dfs_recur(graph, node, visited)

    return visited
print (dfs_recur(graph, 'A', []))
