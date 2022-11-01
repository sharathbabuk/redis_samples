# redis_samples
#Redis with  jedis / lettuce

#To have REDIS on locai follow these instructions

#enable WSL2 on Windows
#Follow thes Microsoft instructions: https://learn.microsoft.com/en-us/windows/wsl/install

#launch Ubantu on windows on your laptop
#Follow these steps to install REDIS on your local Ubuntu
#https://redis.io/docs/getting-started/installation/install-redis-on-windows/

curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list
sudo apt-get update
sudo apt-get install redis

#start the Redis server like so:

sudo service redis-server start

#Connect to Redis
#You can test that your Redis server is running by connecting with the Redis CLI:

redis-cli 
127.0.0.1:6379> ping
PONG

# Redis commands
https://redis.io/docs/manual/cli/

ubuntu@Thinkpad:~$ redis-cli
127.0.0.1:6379> GET greeting
"good morning"
127.0.0.1:6379> SET greeting "Good Evening"
OK
127.0.0.1:6379> GET greeting
"Good Evening"
127.0.0.1:6379>

# scanning for keys

ubuntu@Thinkpad:~$ redis-cli --scan
greeting

# setting expiration and checking for expiration

ubuntu@Thinkpad:~$ redis-cli
127.0.0.1:6379> GET greeting
"good morning"
127.0.0.1:6379> SET greeting "Good Evening"
OK
127.0.0.1:6379> GET greeting
"Good Evening"
127.0.0.1:6379>
127.0.0.1:6379> EXPIRE greeting 60
(integer) 1
127.0.0.1:6379> TTL greeting
(integer) 53
127.0.0.1:6379> TTL greeting
(integer) 49
127.0.0.1:6379> TTL greeting
(integer) 39
127.0.0.1:6379> get greeting
"Good Evening"
127.0.0.1:6379> TTL greeting
(integer) 29
127.0.0.1:6379> TTL greeting
(integer) 16
127.0.0.1:6379> TTL greeting
(integer) -2
127.0.0.1:6379> TTL greeting
(integer) -2
127.0.0.1:6379> get greeting
(nil)
127.0.0.1:6379>

# get keys matching a pattern and DELETE it
ubuntu@Thinkpad:~$ redis-cli --scan --pattern "My*"
Mykey
ubuntu@Thinkpad:~$ redis-cli --scan --pattern "My*" | xargs redis-cli DEL
(integer) 1
ubuntu@Thinkpad:~$ redis-cli --scan --pattern "My*"
ubuntu@Thinkpad:~$
