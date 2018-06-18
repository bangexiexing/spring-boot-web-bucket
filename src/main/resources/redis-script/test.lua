local key = KEYS[1];
local num_str = redis.call("get",key);
if not num_str then
	return 0;
end;
local num = tonumber(num_str);
if num > 0
then
	num = num - 1;
	redis.call("set",key,num);
	return num;
else
	return 0;
end;