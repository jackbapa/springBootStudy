import requests as re
import threading as th
import time
import multiprocessing as mp

SUCCESS_LIST = []
FAIL_LIST = []
CHANEL = mp.Queue(100)

"""

BenchCtrl()
提供多线程和多进程模式

MixBench():
提供混合模式，可开启pNum个进程，每个进程分别启动tNum个线程


测试用例见函数
testP()    #纯进程
testT()    #纯线程
testMix()  #混合，多进程中开多线程
基准测试使用baidu.com
testMix()性能最佳，耗时2.2354111671447754s
"""


class BenchProcess(mp.Process):

    def __init__(self, url, comuQ: mp.Queue):
        '''
        以进程模式发送get请求
        :param url: 测试地址
        :param comuQ: 消息队列，传递引用
        '''
        super().__init__()
        self.comuQ = comuQ
        self.url = url

    def run(self):
        try:
            _ = re.get(self.url)
        except Exception as _:
            self.comuQ.put(False)
            return
        self.comuQ.put(True)
        return


class BenchThread(th.Thread):
    def __init__(self, url):
        '''
        以线程模式发送get请求
        :param url: 测试地址
        '''
        super().__init__()
        self.url = url

    def run(self):
        try:
            resp = re.get(self.url)
        except Exception as e:
            FAIL_LIST.append(e)
            return
        SUCCESS_LIST.append(resp)
        return


class BenchThreadWithQue(BenchThread):
    def __init__(self,url,comuQ):
        super().__init__(url)
        self.Q = comuQ

    def run(self):
        try:
            _ = re.get(self.url)
        except Exception as e:
            self.Q.put(False)
            return
        self.Q.put(True)
        return


class BenchCtrl():
    comuQ = None
    testUrl = None
    ProcessModle = False
    ProcessList = []

    def __init__(self, testUrl, ProcessModle=False):
        '''
        控制线程或进程，提供原始的进程池
        :param testUrl: 测试地址
        :param ProcessModle: 进程模式的开关
        '''
        self.testUrl = testUrl
        self.ProcessModle = ProcessModle

    def runWithThread(self, num):
        for x in range(num):
            BenchTh = BenchThread(self.testUrl)
            BenchTh.start()
        print("========开始测试========")
        print(f"========线程模式，并行数{num}=======")
        startTime = time.time()
        while True:
            successNum = SUCCESS_LIST.__len__()
            failNum = FAIL_LIST.__len__()
            nowTime = time.time()
            print(f"=========目前成功{successNum}次, 失败{failNum}次==========")
            print(f"***********目前耗时:{nowTime - startTime}************")
            if failNum + successNum == num:
                print(f"***********测试结束，总耗时:{nowTime - startTime}************")
                break

    def runWithProcess(self, num):
        self.comuQ = mp.Queue(num)
        print("========多进程模式========")
        for _ in range(num):
            self.ProcessList.append(BenchProcess(self.testUrl, self.comuQ))
        for p in self.ProcessList:
            p.start()
            succesNum = 0
            failNum = 0
        print("========开始测试========")
        startTime = time.time()
        while True:
            if not self.comuQ.empty():
                rep = self.comuQ.get()
                if rep:
                    succesNum += 1
                else:
                    failNum += 1
                nowTime = time.time()
                print(f"=========目前成功{succesNum}次, 失败{failNum}次==========")
                print(f"***********目前耗时:{nowTime - startTime}************")
            if (succesNum + failNum) == num:
                nowTime = time.time()
                print(f"=========结束,目前成功{succesNum}次, 失败{failNum}次==========")
                print(f"***********目前耗时:{nowTime - startTime}************")
                break

    def start(self, num):
        if not self.ProcessModle:
            self.runWithThread(num)
        else:
            self.runWithProcess(num)


class MixBench():
    """
    提供混合模式，
    pNum个进程，每个进程分别启动 tNum个线程
    """
    pNum = None
    tNum = None

    def __init__(self, url, pNum, tNum):
        '''

        :param url: 测试链接
        :param pNum: 进程个数
        :param tNum: 线程个数
        '''
        self.testUrl = url
        self.pNum = pNum
        self.tNum = tNum

    def Worker(self, num, comuQ: mp.Queue):
        _thList = []
        for x in range(num):
            BenchTh = BenchThreadWithQue(self.testUrl, comuQ)
            _thList.append(BenchTh)
            BenchTh.start()
        print("=========正在启动=========")
        for t in _thList:
            t.join()

    def start(self):
        for x in range(self.pNum):
            p = mp.Process(target=self.Worker, args=(self.tNum,CHANEL))
            p.start()
        StartTime = time.time()
        while True:
            if not CHANEL.empty():
                if CHANEL.get():
                    SUCCESS_LIST.append(True)
                else:
                    FAIL_LIST.append(False)
            nowTime = time.time()
            print(f"=========耗时{nowTime-StartTime}s ==========")
            print(f"========成功{SUCCESS_LIST.__len__()}=失败{FAIL_LIST.__len__()}======")
            if SUCCESS_LIST.__len__() + FAIL_LIST.__len__() == (self.tNum*self.pNum):
                print(f"=========耗时{nowTime - StartTime}s ==========")
                break



def testT(url="https://baidu.com", num=50):
    """
    50并发 （纯线程）
    耗时：4.386615037918091
    """
    myBenchTest = BenchCtrl(url, ProcessModle=False)
    myBenchTest.start(num)


def testP(url="https://baidu.com", num=50):
    """
       50并发 （纯进程程）
       耗时：4.482997894287109
    """
    myBenchTest = BenchCtrl(url, ProcessModle=False)
    myBenchTest.start(num)


def testMix():
    """
    50并发（混合模式测试，开5个进程，每个进程中开10个线程）
    耗时：2.2354111671447754s
    :return:
    """
    mixControler = MixBench(url="https://baidu.com", pNum=5, tNum=10)
    mixControler.start()


if __name__ == "__main__":
    testP()
