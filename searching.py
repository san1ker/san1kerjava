from turtle import distance


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


def dfsr(graph, start_node, visited=[]):
    visited.append(start_node)
    for node in graph:
        if node not in visited:
            dfsr(graph, node, visited)
    return visited
#print (dfsr(graph, 'A', []))
    


#Iteration
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
#print (bfs_iter(graph, 'A'))

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

#print (dfs_iter(graph, 'A'))

#Recursive
def dfs_recur(graph, start, visited = []):
    visited.append(start)

    for node in graph:
        if node not in visited:
            dfs_recur(graph, node, visited)

    return visited
#print (dfs_recur(graph, 'A', []))

#https://juhee-maeng.tistory.com/25
#Path
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

#####################################
#dp https://doing7.tistory.com/75
def dp():
    x = 48

    dp = [0 for i in range(50)]

    for i in range(2, x+1):
        dp[i] = dp[i-1]+1

        if i%5 == 0:
            dp[i] = min(dp[i], dp[i//5]+1)
        elif i%3 == 0:
            dp[i] = min(dp[i], dp[i//3]+1)
        elif i%2 == 0:
            dp[i] = min(dp[i], dp[i//2]+1)
    #print (dp)

def dp2_my(storage, each):
    answerodd = 0
    answereven = 0
    for i in range(0, storage, 2):
        answerodd+=each[i]
    for i in range(1, storage, 2):
        answereven+=each[i]

    print (max(answerodd, answereven))

def dp2_answer(storage, each):
    dp = [0 for i in range (50)]

    dp[0] = each[0]
    dp[1] = max(each[0], each[1])

    for i in range(storage):
        dp[i] = max(dp[i-1], dp[i-2]+each[i])
    print (dp)
#dp2_answer(4, [1,3,1,5])

def dijkstra():
    graph = {
        'A': {'B':8, 'C':2, 'D':3},
        'B':{},
        'C':{'B':3, 'D':2},
        'D':{'E':1, 'F':4},
        'E':{},
        'F':{}
    }
    first = 'A'
    dist = {node:float('inf') for node in graph}
    dist[first] = 0
    queue = []
    import heapq

    heapq.heappush(queue, [dist[first], first])

    while queue:
        current_dist, current_node = heapq.heappop(queue)

        if dist[current_node]<current_dist:
            continue
        
        for node, cost in graph[current_node].items():
            total_dist = current_dist+cost

            if total_dist<dist[node]:
                dist[node] = total_dist
                heapq.heappush(queue, [total_dist, node])
    print (dist)
dijkstra()



