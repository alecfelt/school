#!/afs/cats.ucsc.edu/courses/cmps112-wm/usr/smalltalk/bin/gst -f
" Alec Felt   1430374   allfelt "

" extension of class Character for printing "
Character extend [
   isGraph [
      ^ (Character space < self) & (self <= $~)
   ]
   visible [
      self isGraph ifTrue: [^ '$', self asString]
                   ifFalse: [^ self asInteger]
   ]
]

" array for freqTable "
Object subclass: ZeroArray [
   |theArray|

   ZeroArray class >> new [
      self shouldNotImplement.
   ]

	ZeroArray class >> new: size [
      |result|
      result := super new.
      result init: size.
      ^ result
   ]

   init: size [
      theArray := Array new: size.
   ]

   size [
      ^ theArray size.
   ]

   at: index [
      ^ theArray at: index + 1.
   ]

   at: index put: value [
      ^ theArray at: index + 1 put: value.
   ]

   incr: index [
      (self at: index) isNil
         ifTrue: [ self at: index put: 0].
      self at: index put: (self at: index) + 1.
   ]

   keysAndValuesDo: aBlock [
      (0 to: (self size) - 1) do: [:index |
         aBlock value: index value: (self at: index).
      ]
   ]
]

" class for reading the input file "
Object subclass: BitStream [
   |bitIndex byte myStream |
   BitStream class >> new: fileStream [
      |result|
      result := super new.
      result init: fileStream.
      ^result
   ]
   init: fileStream [
      myStream := fileStream.
      bitIndex := 1.
   ]
   nextBit [
      bitIndex = 1 ifTrue: [
         byte := myStream next.
         bitIndex := 9.
      ].
      bitIndex := bitIndex - 1.
      ^byte value bitAt: bitIndex
   ]
   atEnd [
      ^bitIndex = 1 and: [myStream atEnd]
   ]
   currByte [
      ^byte
   ]
]

" class for a leaf "
Object subclass: Leaf [
   |char count|
   char [ ^ char ]
   count [ ^ count ]

   Leaf class >> new [
      self shouldNotImplement
   ]

   Leaf class >> new: aChar count: aCount [
      |result|
      result := super new.
      result setChar: aChar andCount: aCount.
      ^result
   ]

   setChar: aChar andCount: aCount [
      char := aChar.
      count := aCount.
   ]

   <= other [
      ^ (count <= other count)
      | ((count = other count) & (char <= other char))
   ]

   printBase: aStream [
      ^ aStream << self class << '(' << char << ',' << count
   ]

   printOn: aStream [
      (self printBase: aStream) << ')'.
   ]

   inorder: visitor prefix: string [
      visitor value: char value: string.
   ]

	treeEncode: stream [
		stream writeBit: 0.
		(1 to: 8) do: [:i |
			stream writeBit: ((self char asInteger) bitAt: i).
		].
		(char asInteger = 0)
		ifTrue: [
			stream writeBit: 0.
		].
		(char asInteger = 256)
		ifTrue: [
			stream writeBit: 1.
		].
	]

]


Leaf subclass: Tree [
   |left right|

   Tree class >> new: aChar count: aCount [
      self shouldNotImplement
   ]

   Tree class >> new: aChar count: aCount left: aLeft right: aRight [
      |result|
      result := super new: aChar count: aCount.
      result setLeft: aLeft andRight: aRight.
      ^ result
   ]

   setLeft: aLeft andRight: aRight [
      left := aLeft.
      right := aRight.
   ]

	<= other [
      ^ (count < other count)
      | ((count = other count) & (char <= other char))
   	]

   printOn: aStream [
      (self printBase: aStream) << ',' << left << ',' << right << ')'.
   ]

   inorder: visitor prefix: string [
      left inorder: visitor prefix: string, '0'.
      right inorder: visitor prefix: string, '1'.
   ]

	treeEncode: stream [
		left treeEncode: stream.
		right treeEncode: stream.
		stream writeBit: 1.
	]

]

Object subclass: OutBits [
   |bitIndex currentByte myStream|
   OutBits class >> new [
      self shouldNotImplement.
   ]  
   OutBits class >> new: fileStream [
      |result|
      result := super new.
      result init: fileStream.
      ^result
   ]  
   clearByte [
      bitIndex := 8.
      currentByte := 0.
   ]  
   init: fileStream [
      myStream := fileStream.
      self clearByte.
   ]  
   flushByte [
      bitIndex = 8 ifFalse: [
         myStream nextPutByte: currentByte.
         self clearByte.
      ]  
   ]  
   writeBit: bit [
      currentByte := currentByte bitAt: bitIndex put: bit.
      bitIndex := bitIndex - 1.
      bitIndex = 0 ifTrue: [self flushByte].
   ]  
   putString: str [
	  str do: [:i|
		 (i asString = '0')
		 ifTrue: [self writeBit: 0]
		 ifFalse: [self writeBit: 1].	 
	  ].
   ]
   pad [
      [bitIndex < 2 | bitIndex > 8] whileTrue: [
		 self writeBit: 0.
		 bitIndex := bitIndex - 1.
	  ].
   ]
]  


nl := Character nl.
args := Smalltalk arguments.
argsSize := args size.

" Error checking number of command line arguments"
((argsSize < 2) | (argsSize > 3))
	ifTrue: [ stderr display: 'Incorrect number of arguments specified with hzip.st' ; nl ]
	ifFalse: [

" Checks to see if a flag is present "
(((args at: 1) = '-c') | ((args at: 1) = '-t') | ((args at: 1) = '-d') | ((args at: 1) = '-u'))
	ifTrue: [| prioritySize priority cur num count streamFile freqTable stdinBits encTable encTree outFile |
              streamFile := FileStream open: (args at: 2) mode: FileStream read.
			  outFile := stdout.
              (argsSize = 3)
              ifTrue: [
                outFile := FileStream open: (args at: 3) mode: FileStream write.
              ].
		(((args at: 1) = '-c') | ((args at: 1) = '-t') | ((args at: 1) = '-d'))
			ifTrue: [	
" initializing input file stream and freqTable "
freqTable := ZeroArray new: 257.
stdinBits := BitStream new: streamFile.
count := 8.
num := 0.
cur := 0.
" logic to fill freqTable going bit by bit through input file "
freqTable incr: 256.
[stdinBits atEnd] whileFalse: [
	count := count - 1.
	cur := stdinBits nextBit.
	num := (num + (cur * (2 raisedTo: count))).
	count = 0 ifTrue: [
		freqTable incr: num.
		num := 0.
		count := 8.	
	].
].
" logic to ceate priority queue and fill it with leaves from the freqTable "
priority := SortedCollection new.
prioritySize := 0.
freqTable keysAndValuesDo: [:key :value |
	(value notNil and: [value > 0]) ifTrue: [ | leaf |
		leaf := Leaf new: key count: value.
		priority add: leaf.
		prioritySize := (prioritySize + 1).
	].
].
count := prioritySize.
" creates encoding tree "
[count > 1] whileTrue: [ | left right character cnt tree |
	left := priority removeFirst.
	right := priority removeFirst.
	cnt := ((left count) + (right count)).
	character := (left char).
	tree := Tree new: character count: cnt left: left right: right.
	priority add: tree.
	count := (count - 1).
].
encTree := priority removeFirst.
" creates an encoding string table "
encTable := ZeroArray new: 257.
encTree inorder: [:char :string |
	encTable at: char put: string.
] prefix: ''.
" print tree and encoding strings formatted "
((args at: 1) = '-t')
ifTrue: [
	(0 to: 256) do: [:i|
		((freqTable at: i) = nil)
		ifFalse: [ | ch c s |
			ch := (i asCharacter) visible.
			c := (freqTable at: i).
			s := (encTable at: i).
			outFile << ch << '   ' << c << ' ' << s << nl.
		].
	].
].
" compress and print comressed file "
((args at: 1) = '-c')
ifTrue: [| encString bStream |
	bStream := OutBits new: outFile.
	streamFile reset.
	encTree treeEncode: bStream.
	bStream writeBit: 1. "will help for decompressing"
	[streamFile atEnd]
	whileFalse: [ | i |
		i := streamFile next asInteger.
		encString := (encTable at: i).
		bStream putString: encString.
	].
	encString := (encTable at: 256).
	bStream putString: encString.
	streamFile close.
	(argsSize = 3) ifTrue: [
		outFile close.
	].
].
" print debug info "
((args at: 1) = '-d')
ifTrue: [
	freqTable inspect.
	encTree inspect.
	encTable inspect.
].

]
	
" uncompress logic "
ifFalse: [| currByte outStream inStream stack tree treeTemp complete | 
	streamFile reset.
	outStream := BitStream new: streamFile.
	inStream := BitStream new: outFile.
	
	stack := OrderedCollection new.
	complete := false.
	tree := nil.
	[(outStream atEnd not) & (complete = false)] whileTrue: [|ordChar curBit|
		ordChar := outStream nextBit.
		currByte := 0.
		ordChar = 0 ifTrue: [
			(8 to: 1) do: [:i|
				curBit := outStream nextBit.
				currByte at: i put: curBit
			].
			currByte = 0 ifTrue: [
				curbit := outStream nextBit.
				curbit = 1 ifTrue: [
					stack addFirst: (Leaf new: 256 asCharacter count: 1).
				] ifFalse: [
					stack addFirst: (Leaf new: 0 asCharacter count: 1).
				].
			] ifFalse: [
				stack addFirst: (Leaf new: currByte asCharacter count: 1).
			].
		]
		ifFalse: [|right left|
			stack size > 0 ifTrue: [
				right := stack removeFirst.
				stack size = 0 ifTrue: [
					tree := right.
					complete := true
				] ifFalse: [
					left := stack removeFirst.
					stack addFirst: (Tree new: (left char) count: 1 left: left right: right).
				].
			].
		].
	].
	tree inspect.
	treeTemp := tree.
]	


]
" No flag is present, throw an error "
	ifFalse: [ stderr display: 'Incorrect usage: no flag provided'. ]
]
