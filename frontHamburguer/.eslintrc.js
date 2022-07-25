module.exports = {
	root: true,
	env: {
		browser: true,
		node: true
	},
	parserOptions: {
		parser: 'babel-eslint'
	},
	extends: [
    "plugin:vue/essential",
    "plugin:prettier/recommended",
    "eslint:recommended"
  ],
	plugins: [],
	// add your custom rules here
	rules: {
		'vue/component-name-in-template-casing': [ 'error' ],
		'vue/multi-word-component-names': [],
		'vue/return-in-computed-property': [
			'error', { treatUndefinedAsUnspecified: true }
		],
		'vue/component-name-in-template-casing': [ 'error' ],
		'vue/multi-word-component-names': []
	}
}
